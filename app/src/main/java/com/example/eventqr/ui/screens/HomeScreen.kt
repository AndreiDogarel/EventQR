package com.example.eventqr.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventqr.ui.SettingsViewModel
import com.example.eventqr.ui.components.DarkModeSwitch
import com.example.eventqr.ui.components.GradientButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenScanner: () -> Unit,
    onOpenGuests: () -> Unit,
    vm: ClientsViewModel = hiltViewModel()
) {
    val clients = vm.clients.collectAsState()
    val lastAction = vm.lastAction.collectAsState()

    val settingsVm: SettingsViewModel = hiltViewModel()
    val darkMode = settingsVm.darkMode.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(lastAction.value) {
        val msg = when (lastAction.value) {
            "Saved" -> "Saved"
            "Duplicate" -> "Duplicate"
            "Invalid QR" -> "Invalid QR"
            else -> ""
        }
        if (msg.isNotBlank()) snackbarHostState.showSnackbar(msg)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("EventQR") },
                actions = {
                    DarkModeSwitch(
                        checked = darkMode.value,
                        onToggle = { settingsVm.toggleDarkMode() }
                    )
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        FloatingActionButton(
                            onClick = onOpenScanner,
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.QrCodeScanner,
                                contentDescription = null
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, top = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .width(120.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(999.dp))
                    ) {}
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(20.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Checked-in", style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = "${clients.value.size}",
                        style = MaterialTheme.typography.displaySmall
                    )
                    Text(
                        text = "Last: ${lastAction.value}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Guests", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "View and filter checked-in guests",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Box(modifier = Modifier.padding(top = 12.dp)) {
                        GradientButton(
                            text = "Open guests list",
                            onClick = onOpenGuests
                        )
                    }
                }
            }
        }
    }
}
