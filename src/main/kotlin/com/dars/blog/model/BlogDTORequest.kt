package com.dars.blog.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BlogDTORequest(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("content")
    val content: String
)