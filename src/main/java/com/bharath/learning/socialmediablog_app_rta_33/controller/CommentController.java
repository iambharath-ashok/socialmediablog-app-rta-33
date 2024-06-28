package com.bharath.learning.socialmediablog_app_rta_33.controller;


import com.bharath.learning.socialmediablog_app_rta_33.dto.CommentDto;
import com.bharath.learning.socialmediablog_app_rta_33.service.CommentService;
import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class CommentController {

    @Autowired
    private CommentService commentService;


    // POST /v1/api/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable long postId, @RequestBody @Valid CommentDto commentDto) {
       CommentDto savedCommentDto = commentService.createComment(postId, commentDto);
       return new ResponseEntity<>(savedCommentDto, HttpStatus.CREATED);
    }

    //GET /v1/api/posts/{postId}/comments
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> fetchAllCommentsByPostId(@PathVariable long postId) {
       List<CommentDto> commentDtoList = commentService.getAllCommentsByPostId(postId);
       return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }


    //GET /v1/api/posts/{postId}/comments/{commentId}
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> fetchCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId) {
        CommentDto commentDto = commentService.getCommentByPostIdAndCommentId(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    //PUT /v1/api/posts/{postId}/comments/{commentId}
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId, @Valid @RequestBody CommentDto commentDto) {
        CommentDto updatedCommentDto = commentService.updateCommentByPostIdAndCommentId(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedCommentDto, HttpStatus.OK);
    }

    //Delete particular Comment by postId and commentId
    //DELETE /v1/api/posts/{postId}/comments/{commentId}


    // Delete all comments under particular post by PostId
    //DELETE /v1/api/posts/{postId}/comments
    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<String> deleteAllCommentsByPosId(@PathVariable long postId) {
        return new ResponseEntity<>(commentService.deleteAllCommentsOfPostFromPostId(postId), HttpStatus.OK);
    }


    //PATCH /v1/api/posts/{postId}/comments/{commentId}
    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto partiallyUpdateCommentByPostIdAndCommentId(@PathVariable long postId, @PathVariable long commentId, @RequestBody JsonPatch jsonPatch) {
        CommentDto patchedCommentDto =   commentService.updateCommentByPostIdAndCommentIdUsingJsonPatch(postId, commentId, jsonPatch);
        return patchedCommentDto;
    }


}
