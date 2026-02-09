package com.example.eventqr.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable

@Composable
fun DarkModeSwitch(
    checked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val scheme = MaterialTheme.colorScheme

    Switch(
        checked = checked,
        onCheckedChange = onToggle,
        thumbContent = {
            if (checked) {
                Icon(
                    imageVector = Icons.Outlined.NightsStay,
                    contentDescription = null
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.WbSunny,
                    contentDescription = null
                )
            }
        }
    )
}
