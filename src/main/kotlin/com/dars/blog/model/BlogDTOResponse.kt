package com.dars.blog.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BlogDTOResponse(
    @JsonProperty("id")
    val id: Long?,
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("content")
    val content: String?
)