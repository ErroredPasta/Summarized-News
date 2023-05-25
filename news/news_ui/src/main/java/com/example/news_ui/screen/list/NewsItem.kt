package com.example.news_ui.screen.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.compose.SummarizedNewsTheme

@Composable
fun NewsItem(
    uiState: NewsUiState,
    modifier: Modifier = Modifier,
) = with(uiState) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(id) }
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = section,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            text = writtenAt,
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.align(Alignment.End),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsItemPreview() {
    SummarizedNewsTheme {
        NewsItem(
            uiState = NewsUiState(
                id = "",
                title = "Title",
                writtenAt = "2023-02-10T16:37:18Z",
                section = "Media"
            ) {}
        )
    }
}