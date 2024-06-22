package com.bharath.learning.socialmediablog_app_rta_33.controller;


import com.bharath.learning.socialmediablog_app_rta_33.dto.CommentDto;
import com.bharath.learning.socialmediablog_app_rta_33.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // POST /v1/api/posts/{postId}/comments

    @PostMapping("/posts/{postId}/comments")
    public CommentDto createComment(@PathVariable long postId, @RequestBody CommentDto commentDto) {
       CommentDto savedCommentDto = commentService.createComment(postId, commentDto);
       return savedCommentDto;
    }


}
