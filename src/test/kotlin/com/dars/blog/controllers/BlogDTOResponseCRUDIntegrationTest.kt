package com.dars.blog.controllers

import com.dars.blog.BlogApplication
import com.dars.blog.model.BlogDTORequest
import com.dars.blog.model.BlogDTOResponse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
    classes = [BlogApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BlogDTOResponseCRUDIntegrationTest(
    @Autowired var restTemplate: TestRestTemplate
) {
    var blogId: Long = 0

    @BeforeEach
    fun setup() {
        val blogDTORequest = BlogDTORequest("Blog", "content")
        val result = this.restTemplate.postForEntity("/blogs/", blogDTORequest, BlogDTOResponse::class.java)
        blogId = result.body?.id!!
    }

    @Test
    fun getBlogs() {
        val result = this.restTemplate.getForEntity("/blogs/", Array<BlogDTOResponse>::class.java)
        assertTrue { result.body?.size == 1 }
    }

    @Test
    fun `get blog by id not found`() {
        val result = this.restTemplate.getForEntity("/blogs/{id}", BlogDTOResponse::class.java, blogId + 1)
        assertTrue { result.statusCode.is4xxClientError }
    }

    @Test
    fun getBlogById() {
        val result = this.restTemplate.getForEntity("/blogs/{id}", BlogDTOResponse::class.java, blogId)
        assertTrue { result.body?.title.equals("Blog") }
    }

    @Test
    fun updateBlogById() {
        val blogDTORequest = BlogDTORequest("Blog updated", "content updated")
        this.restTemplate.put("/blogs/{id}", blogDTORequest, blogId)
        val result = this.restTemplate.getForEntity("/blogs/{id}", BlogDTOResponse::class.java, blogId)
        assertTrue { result.body?.title.equals("Blog updated") }
    }

    @Test
    fun deleteBlogById() {
        this.restTemplate.delete("/blogs/{id}", blogId)
        val result = this.restTemplate.getForEntity("/blogs/{id}", BlogDTOResponse::class.java, blogId)
        assertTrue { result.statusCode.is4xxClientError }
    }

    @Test
    fun deleteAllBlogs() {
        this.restTemplate.delete("/blogs/")
        val result = this.restTemplate.getForEntity("/blogs/", Array<BlogDTOResponse>::class.java)
        assertTrue { result.body?.size == 0 }
    }
}