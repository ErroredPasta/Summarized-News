package com.example.news_ui.screen.detail

import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core_ui.compose.SummarizedNewsTheme
import com.example.news_domain.model.NewsDetail
import com.example.news_ui.R


@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsDetailViewModel = viewModel(),
) {
    val newsDetailState by viewModel.state.collectAsStateWithLifecycle()

    NewsDetailScreenContent(
        modifier = modifier,
        newsDetailState = newsDetailState
    )
}

@Composable
fun NewsDetailScreenContent(
    modifier: Modifier = Modifier,
    newsDetailState: NewsDetailState,
) = with(newsDetailState) {
    val backgroundColor = MaterialTheme.colors.surface

    Box(modifier = modifier.background(backgroundColor)) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } else {
            val scrollState = rememberScrollState()
            val bodyHtmlString = remember(data.body) {
                HtmlCompat.fromHtml(data.body, FROM_HTML_MODE_COMPACT)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = contentColorFor(backgroundColor)
                )
                Text(
                    text = data.section,
                    fontSize = 14.sp,
                    color = contentColorFor(backgroundColor)
                )
                Text(
                    text = data.writtenAt,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )

                if (summary != null) {
                    TextBoxWithTitle(
                        title = stringResource(id = R.string.summary),
                        body = "Summary body text",
                        backgroundColor = MaterialTheme.colors.primary,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (error != null) {
                    TextBoxWithTitle(
                        title = stringResource(id = R.string.error_occurred),
                        body = error.message
                            ?: stringResource(id = R.string.error_occurred_while_getting_news),
                        backgroundColor = Color.Red,
                        textColor = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                AndroidView(
                    factory = {
                        TextView(it).apply {
                            val textColor = AppCompatResources.getColorStateList(it, R.color.black)
                            setTextColor(textColor)
                        }
                    },
                    update = { textView -> textView.text = bodyHtmlString }
                )
            }
        }
    }
}

@Composable
fun TextBoxWithTitle(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    backgroundColor: Color,
    textColor: Color = contentColorFor(backgroundColor = backgroundColor),
) {
    Column(
        modifier = modifier
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor)
        Text(text = body, color = textColor)
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryFailureBoxPreview() {
    SummarizedNewsTheme {
        Surface {
            TextBoxWithTitle(
                title = stringResource(id = R.string.error_occurred),
                body = "Error message text",
                backgroundColor = Color.Red,
                textColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryBoxPreview() {
    SummarizedNewsTheme {
        Surface {
            TextBoxWithTitle(
                title = stringResource(id = R.string.summary),
                body = "Summary body text",
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenContentLoadingPreview() {
    SummarizedNewsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NewsDetailScreenContent(
                newsDetailState = NewsDetailState(isLoading = true)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenContentSuccessPreview() {
    SummarizedNewsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NewsDetailScreenContent(
                newsDetailState = NewsDetailState(
                    data = NewsDetail(
                        id = "",
                        title = "Title",
                        section = "Media",
                        writtenAt = "2023-02-10T16:37:18Z",
                        body = "News body text"
                    ),
                    summary = "Summary content text",
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsDetailScreenContentErrorPreview() {
    SummarizedNewsTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            NewsDetailScreenContent(
                newsDetailState = NewsDetailState(
                    error = Exception("Exception message")
                )
            )
        }
    }
}