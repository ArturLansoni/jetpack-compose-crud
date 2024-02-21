package com.arturlansoni.servicelist.presentation.ui.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Rating(
    value: Double,
    onValueChange: (Double) -> Unit,
    maxRating: Int
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxRating) { index ->
            val isSelected = index < value
            val icon = if (isSelected) Icons.Filled.Star else Icons.Filled.StarBorder
            val tint = if (isSelected) Color.Gray else Color.Gray

            IconButton(onClick = { onValueChange(index +  1.0) }) {
                Icon(icon, contentDescription = "Star", tint = tint)
            }
        }
    }
}