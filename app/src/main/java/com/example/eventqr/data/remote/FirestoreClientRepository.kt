package com.example.eventqr.data.remote

import com.example.eventqr.domain.model.Client
import com.example.eventqr.domain.model.ClientType
import com.example.eventqr.domain.repo.ClientRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

class FirestoreClientRepository(
    private val firestore: FirebaseFirestore,
    private val authManager: FirebaseAuthManager,
    private val eventIdProvider: EventIdProvider
) : ClientRepository {

    private fun clientsCol() =
        firestore.collection("events")
            .document(eventIdProvider.getEventId())
            .collection("clients")

    override suspend fun addClientIgnoreDuplicate(client: Client): Boolean {
        authManager.ensureSignedIn()
        val docId = sha256Hex(client.qrRaw)
        val ref = clientsCol().document(docId)

        return try {
            firestore.runTransaction { tx ->
                val snap = tx.get(ref)
                if (snap.exists()) return@runTransaction false
                tx.set(ref, clientToMap(client.copy(id = docId)))
                true
            }.await()
        } catch (e: Exception) {
            false
        }
    }

    override fun observeAll(): Flow<List<Client>> =
        observeQuery(clientsCol().orderBy("scannedAt", Query.Direction.DESCENDING))

    override fun observeByType(type: String): Flow<List<Client>> =
        observeQuery(
            clientsCol()
                .whereEqualTo("clientType", type)
                .orderBy("scannedAt", Query.Direction.DESCENDING)
        )

    override fun observeByCompany(company: String): Flow<List<Client>> =
        observeQuery(
            clientsCol()
                .whereEqualTo("company", company)
                .orderBy("scannedAt", Query.Direction.DESCENDING)
        )

    override fun observeByTypeAndCompany(type: String, company: String): Flow<List<Client>> =
        observeQuery(
            clientsCol()
                .whereEqualTo("clientType", type)
                .whereEqualTo("company", company)
                .orderBy("scannedAt", Query.Direction.DESCENDING)
        )

    private fun observeQuery(query: com.google.firebase.firestore.Query): Flow<List<Client>> = callbackFlow {
        val reg = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(emptyList())
                return@addSnapshotListener
            }
            val docs = snapshot?.documents.orEmpty()
            val clients = docs.mapNotNull { d ->
                val id = d.id
                val firstName = d.getString("firstName") ?: return@mapNotNull null
                val lastName = d.getString("lastName") ?: return@mapNotNull null
                val clientTypeStr = d.getString("clientType") ?: ClientType.DCS_CLIENT.name
                val company = d.getString("company") ?: ""
                val qrRaw = d.getString("qrRaw") ?: ""
                val scannedAt = d.getLong("scannedAt") ?: 0L
                val type = runCatching { ClientType.valueOf(clientTypeStr) }.getOrElse { ClientType.DCS_CLIENT }
                Client(
                    id = id,
                    firstName = firstName,
                    lastName = lastName,
                    clientType = type,
                    company = company,
                    qrRaw = qrRaw,
                    scannedAt = scannedAt
                )
            }
            trySend(clients)
        }
        awaitClose { reg.remove() }
    }

    private fun clientToMap(client: Client): Map<String, Any> =
        mapOf(
            "firstName" to client.firstName,
            "lastName" to client.lastName,
            "clientType" to client.clientType.name,
            "company" to client.company,
            "qrRaw" to client.qrRaw,
            "scannedAt" to client.scannedAt
        )

    private fun sha256Hex(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { b -> "%02x".format(b) }
    }
}
