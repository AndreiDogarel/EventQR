package com.example.eventqr.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    navigation: (@Composable () -> Unit)?,
    actions: (@Composable () -> Unit)?,
    snackbarHostState: SnackbarHostState,
    floatingActionButton: (@Composable () -> Unit)?,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = { navigation?.invoke() },
                actions = { actions?.invoke() }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = { floatingActionButton?.invoke() }
    ) { padding ->
        content(padding)
    }
}
