package com.trunghoang.generalapp.model

data class Movie(
    var title: String,
    var imageUrl: String,
    var isLiked: Boolean = false
)