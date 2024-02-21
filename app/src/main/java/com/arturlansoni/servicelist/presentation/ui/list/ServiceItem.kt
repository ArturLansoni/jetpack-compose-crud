package com.arturlansoni.servicelist.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun ServiceItem(
    name: String,
    location: String,
    rating: Double,
    category: String,
    imageURL: String,
    onPress: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
            .wrapContentHeight()
            .clickable { onPress() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageURL,
            contentDescription = "$name logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(50)
                )
        )
        Column(modifier = Modifier.padding(start = 12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Rating",
                    modifier = Modifier.size(12.dp),
                )
                Text(
                    rating.toString(),
                    fontSize = 12.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(25)
                        )
                        .absolutePadding(6.dp, 6.dp, 6.dp, 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        category,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Text(
                location,
                fontSize = 12.sp,
            )
        }
    }
}
