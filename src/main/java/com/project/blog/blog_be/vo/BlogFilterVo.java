package com.project.blog.blog_be.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogFilterVo {

    Object filter;

    String start_cursor;
    Integer page_size;

}
