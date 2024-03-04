package com.project.blog.blog_be.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Locale.Category;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blog.blog_be.service.impl.BlogService;
import com.project.blog.blog_be.vo.BlogFilterVo;
import com.project.blog.blog_be.vo.CategoryVO;
import com.project.blog.blog_be.vo.TagVO;

import lombok.val;

@Service("BlogService")
@SuppressWarnings("unchecked")
public class BlogServiceImpl implements BlogService {

    static String apiKey = "Bearer secret_1Eg9kSfYrb30eK6V4Gwco9ktsa17wOz85wVHJviGzW4";
    static String dbId = "4ca72bec3efb4960894023354443b5a7";
    static String url = "https://api.notion.com/v1/";

    ObjectMapper objectMapper = new ObjectMapper();

    WebClient webClient =
    WebClient
            .builder()
            .baseUrl(url + "/")
            .defaultHeader("Notion-Version", "2022-06-28")
            .defaultHeader("Authorization", apiKey)
            .build();
    @Override
    public Map<String,Object> getBlogPost(String tag, String category, String cursor ) throws Exception {

        Map<String, Object> filterVo = new HashMap<>();

        CategoryVO categories = new CategoryVO();
        TagVO tags = new TagVO();

        if(StringUtils.hasLength(tag)){
                Map<String, Object> sel = new HashMap<>();
                sel.put("contains", tag);
                tags.setProperty("tags");
                tags.setMulti_select(sel);
        }       
        if(StringUtils.hasLength(category)){
                Map<String, Object> sel = new HashMap<>();
                sel.put("equals", category);
                categories.setProperty("category");
                categories.setSelect(sel);
        }

        if(StringUtils.hasLength(tag) && StringUtils.hasLength(category)){
                Map<String, List<Object>> andFilter = new HashMap<>();
                andFilter.put("and", List.of(categories, tags));
                filterVo.put("filter",andFilter);
        }else if(StringUtils.hasLength(tag)){
                filterVo.put("filter",tags);
        }else if(StringUtils.hasLength(category)){
                filterVo.put("filter",categories);
        }

        if(StringUtils.hasLength(cursor)){
              filterVo.put("start_cursor", cursor);
        }

        filterVo.put("page_size",10);

        // api 요청
        Map<String, Object> response =
        webClient
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("databases/" + dbId + "/query")
                                .build())
                                .bodyValue(filterVo)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

                System.out.println(response);

        return response;
    }

    @Override
    public List<Object> getCategories() {

        // api 요청
        Map<String, Object> response =
        webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("databases/"+ dbId)
                                .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Map<String,Map<String, List<Object>>>> props = objectMapper.convertValue(response.get("properties"), Map.class);
        List<Object> categories = props.get("category").get("select").get("options");

        return categories;
    }

    @Override
    public List<Object> getTags(){

        // api 요청
        Map<String, Object> response =
        webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("databases/"+ dbId)
                                .build())
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, Map<String,Map<String, List<Object>>>> props = objectMapper.convertValue(response.get("properties"), Map.class);
        List<Object> tags = props.get("tags").get("multi_select").get("options");

        return tags;
    }
}
