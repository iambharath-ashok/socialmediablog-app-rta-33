package com.bharath.learning.socialmediablog_app_rta_33.service;

import com.bharath.learning.socialmediablog_app_rta_33.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPostId(long postId);

    CommentDto getCommentByPostIdAndCommentId(long postId, long commentId);
}
