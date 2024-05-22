package com.dars.blog.controllers

import com.dars.blog.model.BlogDTORequest
import com.dars.blog.services.BlogService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/blogs")
class BlogController(private val service: BlogService) {

    @PostMapping("/")
    fun createBlog(@RequestBody newBlog: BlogDTORequest) = service.createBlog(newBlog)

    @GetMapping("/")
    fun getBlogs() = service.getBlogs()

    @GetMapping("/{id}")
    fun getBlogById(@PathVariable id: Long) = service.getBlogById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Blog not found")

    @PutMapping("/{id}")
    fun updateBlogById(@PathVariable id: Long, @RequestBody newBlog: BlogDTORequest) =
        service.updateBlogById(id, newBlog)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Blog not found")

    @DeleteMapping("/{id}")
    fun deleteBlogById(@PathVariable id: Long) = service.getBlogById(id)?.let { service.deleteBlogById(id) }
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Blog not found")

    @DeleteMapping("/")
    fun deleteAllBlogs() = service.deleteAllBlogs()
}