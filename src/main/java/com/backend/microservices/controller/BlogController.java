package com.backend.microservices.controller;

import com.backend.microservices.model.Blog;
import com.backend.microservices.model.User;
import com.backend.microservices.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    /**
     * Create blog (only logged-in user)
     */
    @PostMapping
    public ResponseEntity<?> createBlog(
            @RequestBody Blog blog,
            @AuthenticationPrincipal User user
    ) {
        Blog created = blogService.createBlog(blog, user);
        return ResponseEntity.ok(created);
    }

    /**
     * Get all blogs
     */
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    /**
     * Get logged-in user's blogs
     */
    @GetMapping("/me")
    public ResponseEntity<List<Blog>> getMyBlogs(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(blogService.getBlogsByUser(user));
    }

    /**
     * Update blog (only owner)
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(
            @PathVariable Long id,
            @RequestBody Blog blog,
            @AuthenticationPrincipal User user
    ) {
        Optional<Blog> updated = blogService.updateBlog(id, blog, user);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }

        return ResponseEntity
                .badRequest()
                .body("Blog not found or you are not the owner");
    }

    /**
     * Delete blog (only owner)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        boolean deleted = blogService.deleteBlog(id, user);

        if (deleted) {
            return ResponseEntity.ok("Blog deleted successfully");
        }

        return ResponseEntity
                .badRequest()
                .body("Blog not found or you are not the owner");
    }
}
