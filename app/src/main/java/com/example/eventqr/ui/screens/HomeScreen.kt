package com.example.eventqr.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    onOpenScanner: () -> Unit,
    vm: ClientsViewModel = hiltViewModel()
) {
    val clients = vm.clients.collectAsState()
    val lastAction = vm.lastAction.collectAsState()
    val textState = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(24.dp)) {
        Text("EventQR")
        Text("Clients (shared): ${clients.value.size}")
        Text("Last: ${lastAction.value}")

        Button(
            onClick = onOpenScanner,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Open QR scanner")
        }

        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            label = { Text("QR: firstName|lastName|type|company") },
            modifier = Modifier.padding(top = 20.dp)
        )

        Button(
            onClick = { vm.onQrScanned(textState.value) },
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Simulate scan")
        }
    }
}
