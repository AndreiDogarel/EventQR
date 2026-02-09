package com.example.eventqr.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventqr.domain.model.Client
import com.example.eventqr.domain.model.ClientType
import com.example.eventqr.ui.components.AppScaffold
import com.example.eventqr.ui.components.BackButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember



@Composable
fun GuestsScreen(
    onBack: () -> Unit,
    vm: ClientsViewModel = hiltViewModel()
) {
    val filtered = vm.filteredClients.collectAsState()
    val total = vm.clients.collectAsState()
    val typeFilter = vm.typeFilter.collectAsState()
    val companyFilter = vm.companyFilter.collectAsState()
    val searchQuery = vm.searchQuery.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    AppScaffold(
        title = "Guests",
        navigation = { BackButton(onBack) },
        actions = null,
        snackbarHostState = snackbarHostState,
        floatingActionButton = null
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(20.dp)) {

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Total: ${total.value.size}")
                    Text("Shown: ${filtered.value.size}")
                }
            }

            Row(modifier = Modifier.padding(top = 16.dp)) {
                TypeChip(
                    text = "All",
                    selected = typeFilter.value == null,
                    onClick = { vm.setTypeFilter(null) }
                )
                TypeChip(
                    text = "DCS",
                    selected = typeFilter.value == ClientType.DCS_EMPLOYEE,
                    onClick = { vm.setTypeFilter(ClientType.DCS_EMPLOYEE) }
                )
                TypeChip(
                    text = "Vendors",
                    selected = typeFilter.value == ClientType.VENDOR_PARTNER,
                    onClick = { vm.setTypeFilter(ClientType.VENDOR_PARTNER) }
                )
                TypeChip(
                    text = "Clients",
                    selected = typeFilter.value == ClientType.DCS_CLIENT,
                    onClick = { vm.setTypeFilter(ClientType.DCS_CLIENT) }
                )
            }

            OutlinedTextField(
                value = companyFilter.value,
                onValueChange = { vm.setCompanyFilter(it) },
                label = { Text("Company filter") },
                modifier = Modifier.padding(top = 12.dp).fillMaxWidth()
            )

            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { vm.setSearchQuery(it) },
                label = { Text("Search (name/company)") },
                modifier = Modifier.padding(top = 12.dp).fillMaxWidth()
            )

            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                items(filtered.value, key = { it.id }) { client ->
                    GuestCard(client)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                }
            }
        }
    }
}

@Composable
private fun TypeChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Text(if (selected) "[$text]" else text)
    }
}

@Composable
private fun GuestCard(client: Client) {
    val time = formatTime(client.scannedAt)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "${client.firstName} ${client.lastName}",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = client.company,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp)
            )

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TypeBadge(client.clientType.name)

                Text(
                    text = time,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun TypeBadge(type: String) {
    val scheme = MaterialTheme.colorScheme

    val container = when (type) {
        "DCS_EMPLOYEE" -> scheme.primary
        "VENDOR_PARTNER" -> scheme.secondary
        "DCS_CLIENT" -> scheme.tertiary
        else -> scheme.surfaceVariant
    }

    val content = when (type) {
        "DCS_EMPLOYEE", "VENDOR_PARTNER", "DCS_CLIENT" -> scheme.onPrimary
        else -> scheme.onSurfaceVariant
    }

    Surface(
        color = container,
        contentColor = content,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = type,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

private fun formatTime(ts: Long): String {
    val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return fmt.format(Date(ts))
}
