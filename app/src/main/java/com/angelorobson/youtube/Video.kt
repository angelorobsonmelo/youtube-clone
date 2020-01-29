package com.angelorobson.youtube

import java.text.SimpleDateFormat
import java.util.*

data class Video(
    val id: String,
    val thumbnailUrl: String,
    val title: String,
    val publishedAt: Date,
    val viewsCount: Long,
    val viewsCountLabel: String,
    val duration: Int,
    val videoUrl: String,
    val publisher: Publisher
)

data class Publisher(
    val id: String,
    val name: String,
    val pictureProfileUrl: String
)

data class ListVideo(
    val status: Int,
    val data: List<Video>
)

fun Date.formatted(): String =
    SimpleDateFormat("d MMM yyyy", Locale("pt", "BR")).format(this)