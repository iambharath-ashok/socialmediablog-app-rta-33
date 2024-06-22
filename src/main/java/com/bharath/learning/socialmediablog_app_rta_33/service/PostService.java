package com.bharath.learning.socialmediablog_app_rta_33.service;

import com.bharath.learning.socialmediablog_app_rta_33.dto.PostDto;
import com.bharath.learning.socialmediablog_app_rta_33.payload.PostResponse;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();
    PostResponse getAllPosts(int pageNo, int pageSize);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection);

    PostDto getPostById(long id);

    PostDto createPost(PostDto postDto);

    PostDto updatePost(PostDto postDto, long postId);

    void deletePostById(long id);

}
