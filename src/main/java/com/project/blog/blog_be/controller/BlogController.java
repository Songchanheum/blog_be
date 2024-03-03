package com.project.blog.blog_be.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {
    @GetMapping("/post")
    public String getPost(){
        return "123";
    }
}