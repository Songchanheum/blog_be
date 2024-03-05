package com.project.blog.blog_be.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.blog_be.service.impl.BlogService;



@RestController
public class BlogController {

    @Autowired
    public BlogService blogService;

    @GetMapping("/posts")
    public Map<String,Object> getPosts(@RequestParam(value="tag", required = false) String tag, @RequestParam(value="category", required = false) String category, @RequestParam(value="cursor", required = false) String cursor) throws Exception{
        Map<String,Object> result = blogService.getBlogPostList(tag, category, cursor);
        return result;
    }
    @GetMapping("/post")
    public Map<String,Object> getPost(@RequestParam(value="id", required = true) String id) throws Exception{
        Map<String,Object> result = blogService.getBlogPost(id);
        return result;
    }
    @GetMapping("/categories")
    public List<Object> getCategories() {
        return blogService.getCategories();
    }
    @GetMapping("/tags")
    public List<Object> getTags() {
        return blogService.getTags();
    }
    
}