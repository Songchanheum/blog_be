package com.project.blog.blog_be.service.impl;

import java.util.List;
import java.util.Map;

public interface BlogService {

    public Map<String,Object> getBlogPost(String tag, String category, String cursor) throws Exception;
    public List<Object> getCategories();
    public List<Object> getTags();
}
