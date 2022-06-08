package com.ajgroup.themoviedbnew.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ajgroup.themoviedbnew.ui.home.IMAGE_BASE

@Composable
fun CustomDetails(
    title: String,
    posterPath: String,
    genres: List<String>,
    rating: String,
    dateLabel: String,
    dateData: String,
    tagline: String?,
    overview: String?,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column{
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PosterWithRating(posterPath = posterPath, rating = rating)
                DetailHeader(
                    title = title,
                    genres = genres,
                    dateLabel = dateLabel,
                    dateData = dateData
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TaglineAndOverview(tagline, overview)
        }
    }
}
@Composable
fun PosterWithRating(
    posterPath: String,
    rating: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(
            width = 128.dp,
            height = 192.dp
        )
    ) {
        Image(painter = rememberAsyncImagePainter(model = "${IMAGE_BASE}${posterPath}"), contentDescription ="",
        modifier = Modifier.matchParentSize().clip(RoundedCornerShape(16.dp)),
        )
    }
}
@Composable
fun DetailHeader(
    title: String,
    genres: List<String>,
    dateLabel: String,
    dateData: String
){
    Column {
        Text(
            text = title,
            maxLines = 3,
            style = MaterialTheme.typography.h1,
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Genre",
            fontSize = 12.sp,
        )
        Text(text = genres.joinToString(separator = ", "))

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dateLabel,
            fontSize = 12.sp,
        )
        Text(text = dateData)
    }
}
@Composable
fun TaglineAndOverview(
    tagline: String?,
    overview: String?,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = tagline ?: "",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = overview ?: "",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Justify,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}