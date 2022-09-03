package com.example.naoandroidclient.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.domain.ControllerDirection


@Composable
fun Controller(onDirectionChange: (ControllerDirection) -> Unit ) {
    val buttonSize = Modifier.padding(40.dp)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        ControllerIconButton(icon = Icons.Default.KeyboardArrowUp) {
            onDirectionChange.invoke(ControllerDirection.FORWARD)
        }
        Row {
            ControllerIconButton(icon = Icons.Default.KeyboardArrowLeft) {
                onDirectionChange.invoke(ControllerDirection.LEFT)
            }

            Spacer(modifier = buttonSize)
            ControllerIconButton(icon = Icons.Default.KeyboardArrowRight) {
                onDirectionChange.invoke(ControllerDirection.RIGHT)

            }
        }
        ControllerIconButton(icon = Icons.Default.KeyboardArrowDown) {
            onDirectionChange.invoke(ControllerDirection.BACKWARD)
        }
    }
}

@Composable
fun ControllerIconButton(modifier: Modifier = Modifier, icon: ImageVector, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
        ,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}
