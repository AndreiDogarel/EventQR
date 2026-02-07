package com.example.eventqr.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlaceholderScreen() {
    Text(
        text = "Setup OK",
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    )
}