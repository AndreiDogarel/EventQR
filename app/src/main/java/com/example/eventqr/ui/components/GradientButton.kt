package com.example.eventqr.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background


@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val brush = Brush.horizontalGradient(
        listOf(scheme.secondary, scheme.primary)
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        color = androidx.compose.ui.graphics.Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(brush)
                .padding(vertical = 14.dp, horizontal = 16.dp)
        ) {
            Text(
                text = text,
                color = scheme.onPrimary,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
