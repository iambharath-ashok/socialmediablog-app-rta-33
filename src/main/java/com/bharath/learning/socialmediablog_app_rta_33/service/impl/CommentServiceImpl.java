package com.bharath.learning.socialmediablog_app_rta_33.service.impl;

import com.bharath.learning.socialmediablog_app_rta_33.dto.CommentDto;
import com.bharath.learning.socialmediablog_app_rta_33.excpetions.ResourceNotFoundException;
import com.bharath.learning.socialmediablog_app_rta_33.model.CommentEntity;
import com.bharath.learning.socialmediablog_app_rta_33.model.PostEntity;
import com.bharath.learning.socialmediablog_app_rta_33.repository.CommentRepository;
import com.bharath.learning.socialmediablog_app_rta_33.repository.PostRepository;
import com.bharath.learning.socialmediablog_app_rta_33.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {

        //Map CommentDTO to CommentEntity
        CommentEntity commentEntity = convertDtoToEntity(commentDto);

        //Fetch Post from Post Repository using PostId
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(postId)));

        //Set Comment to particular Post
        commentEntity.setPostEntity(postEntity);

        //Save Comment Entity to dB
        CommentEntity savedCommentEntity  = commentRepository.save(commentEntity);

        //Map Comment Entity to Comment DTO
       CommentDto updatedCommentDto =  convertEntityToDto(savedCommentEntity);

        return updatedCommentDto;
    }



    @Override
    public List<CommentDto> getAllCommentsByPostId(long postId) {
        return null;
    }

    @Override
    public CommentDto getCommentByPostIdAndCommentId(long postId, long commentId) {
        return null;
    }


    private CommentEntity convertDtoToEntity(CommentDto commentDto) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUserName(commentDto.getUserName());
        commentEntity.setEmail(commentDto.getEmail());
        commentEntity.setBody(commentDto.getBody());
        return commentEntity;
    }


    private CommentDto convertEntityToDto(CommentEntity commentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setUserName(commentEntity.getUserName());
        commentDto.setEmail(commentEntity.getEmail());
        commentDto.setBody(commentEntity.getBody());
        return commentDto;
    }
}
