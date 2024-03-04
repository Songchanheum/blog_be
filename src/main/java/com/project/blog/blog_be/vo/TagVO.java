package com.project.blog.blog_be.vo;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagVO {

    String property;
    Map<String, Object> multi_select;
    
}
