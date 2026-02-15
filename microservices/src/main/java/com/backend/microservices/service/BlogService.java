package com.backend.microservices.service;

import com.backend.microservices.model.Blog;
import com.backend.microservices.model.User;
import com.backend.microservices.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    /**
     * Create blog for logged-in user
     */
    public Blog createBlog(Blog blog, User user) {
        blog.setUser(user);   // attach owner
        return blogRepository.save(blog);
    }

    /**
     * Get all blogs in system
     */
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    /**
     * Get only logged-in user's blogs
     */
    public List<Blog> getBlogsByUser(User user) {
        return blogRepository.findByUser(user);
    }

    /**
     * Update blog (only owner allowed)
     */
    @Transactional
    public Optional<Blog> updateBlog(Long blogId, Blog updatedBlog, User user) {

        Optional<Blog> existing = blogRepository.findById(blogId);

        if (existing.isPresent() && existing.get().getUser().getId().equals(user.getId())) {
            Blog blog = existing.get();
            blog.setTitle(updatedBlog.getTitle());
            blog.setContent(updatedBlog.getContent());
            return Optional.of(blog);
        }

        return Optional.empty(); // not owner or not found
    }

    /**
     * Delete blog (only owner allowed)
     */
    @Transactional
    public boolean deleteBlog(Long blogId, User user) {

        Optional<Blog> existing = blogRepository.findById(blogId);

        if (existing.isPresent() && existing.get().getUser().getId().equals(user.getId())) {
            blogRepository.delete(existing.get());
            return true;
        }

        return false; // not owner or not found
    }
}
