package com.bharath.learning.socialmediablog_app_rta_33.controller;


import com.bharath.learning.socialmediablog_app_rta_33.payload.PostResponse;
import com.bharath.learning.socialmediablog_app_rta_33.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/api/posts")
public class PostControllerV2 {

    @Autowired
    private PostService postService;

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return postService.getAllPosts(pageNo, pageSize);
    }

}
