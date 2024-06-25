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
import java.util.stream.Collectors;

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

       List<CommentEntity> commentEntities = commentRepository.findByPostId(postId);
       if(commentEntities != null && !commentEntities.isEmpty()) {
         return  commentEntities.stream().map(commentEntity -> convertEntityToDto(commentEntity)).collect(Collectors.toList());
       }
        return null;
    }

    @Override
    public CommentDto getCommentByPostIdAndCommentId(long postId, long commentId) {

        //Fetch Post Entity using Post Repository from postId
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(postId)) );


        //Fetch Comment Entity using Comment Repository from commentId
       CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", String.valueOf(commentId)) );

        //Validate comment belong to that particular Post
       if(!commentEntity.getPostEntity().getId().equals(postEntity.getId())) {
           throw new RuntimeException("Bad Request:: Comment Not Found");
       }


        //Map Comment Entity to Comment Dto
        return convertEntityToDto(commentEntity);
    }

    @Override
    public CommentDto updateCommentByPostIdAndCommentId(long postId, long commentId, CommentDto commentDto) {

        //Fetch Post Entity using Post Repository from postId
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(postId)) );


        //Fetch Comment Entity using Comment Repository from commentId
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", String.valueOf(commentId)) );

        //Validate comment belong to that particular Post
        if(!commentEntity.getPostEntity().getId().equals(postEntity.getId())) {
            throw new RuntimeException("Bad Request:: Comment Not Found");
        }


        //Update old Comment Details with new Comment Dto
        commentEntity.setBody(commentDto.getBody());
        commentEntity.setUserName(commentDto.getUserName());
        commentEntity.setEmail(commentDto.getEmail());

        //Save Comment Entity to DB
        CommentEntity newlySavedCommentEntity = commentRepository.save(commentEntity);

        //Map Comment Entity to Comment DTO
        return convertEntityToDto(newlySavedCommentEntity);
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
