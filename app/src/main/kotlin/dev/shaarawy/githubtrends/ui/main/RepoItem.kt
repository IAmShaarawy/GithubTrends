package dev.shaarawy.githubtrends.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import dev.shaarawy.githubtrends.presentation.main.item.ItemViewModel

@Composable
fun RepoItem(itemViewModel: ItemViewModel) {
    val languageColor = remember {
        try {
            itemViewModel.model?.colorHex?.toColorInt()?.let { Color(it) } ?: Color.Black
        } catch (t: Throwable) {
            Color.Black
        }
    }
    Row(
        modifier = Modifier
            .padding(start = 16.dp),
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = itemViewModel.model?.owner?.avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 24.dp)
                .border(width = 1.dp, color = MaterialTheme.colors.secondary, shape = CircleShape)
                .clip(CircleShape)
                .size(40.dp)
        )
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = itemViewModel.model?.owner?.name ?: "N/A",
                style = MaterialTheme.typography.body2
            )
            Text(text = itemViewModel.model?.name ?: "N/A", style = MaterialTheme.typography.h6)
            Text(
                text = itemViewModel.model?.description ?: "N/A",
                style = MaterialTheme.typography.body2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .border(width = 1.dp, color = MaterialTheme.colors.secondary, shape = CircleShape)
                            .clip(CircleShape)
                            .size(12.dp)
                            .background(languageColor)
                    ) {}
                    Text(
                        text = itemViewModel.model?.language ?: "N/A",
                        style = MaterialTheme.typography.caption,
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFD700)
                    )
                    Text(
                        text = itemViewModel.model?.starsCount?.toString() ?: "N/A",
                        style = MaterialTheme.typography.caption
                    )
                }
            }
        }
    }
}