package com.dars.blog.services

import com.dars.blog.model.BlogDTORequest
import com.dars.blog.model.BlogDTOResponse
import com.dars.blog.repositories.BlogRepository
import com.dars.blog.repositories.entities.BlogEntity
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class BlogService(private val repository: BlogRepository) {

    fun createBlog(newBlog: BlogDTORequest) =
        repository.save(newBlog.toEntity()).toDTO()

    fun getBlogs() =
        repository.findAll().map { it.toDTO() }

    fun getBlogById(id: Long) =
        repository.findById(id).map { it.toDTO() }.getOrNull()

    fun updateBlogById(id: Long, newBlog: BlogDTORequest) =
        repository.findById(id).getOrNull()?.run {
            title = newBlog.title
            content = newBlog.content
            repository.save(this).toDTO()
        }

    fun deleteBlogById(id: Long) =
        repository.deleteById(id)

    fun deleteAllBlogs() =
        repository.deleteAll()
}

/**
 * Extension functions
 */
private fun BlogEntity.toDTO(): BlogDTOResponse {
    return BlogDTOResponse(
        id = id!!,
        title = title,
        content = content
    )
}

private fun BlogDTORequest.toEntity(): BlogEntity {
    return BlogEntity(
        id = null,
        title = title,
        content = content
    )
}