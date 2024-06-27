package com.bharath.learning.socialmediablog_app_rta_33.service.impl;

import com.bharath.learning.socialmediablog_app_rta_33.dto.CommentDto;
import com.bharath.learning.socialmediablog_app_rta_33.excpetions.ResourceNotFoundException;
import com.bharath.learning.socialmediablog_app_rta_33.model.CommentEntity;
import com.bharath.learning.socialmediablog_app_rta_33.model.PostEntity;
import com.bharath.learning.socialmediablog_app_rta_33.repository.CommentRepository;
import com.bharath.learning.socialmediablog_app_rta_33.repository.PostRepository;
import com.bharath.learning.socialmediablog_app_rta_33.service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;


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

    @Override
    public CommentDto updateCommentByPostIdAndCommentIdUsingJsonPatch(long postId, long commentId, JsonPatch jsonPatch) {
        //Fetch Post Entity using Post Repository from postId
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", String.valueOf(postId)) );


        //Fetch Comment Entity using Comment Repository from commentId
        CommentEntity commentEntity = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", String.valueOf(commentId)) );

        //Validate comment belong to that particular Post
        if(!commentEntity.getPostEntity().getId().equals(postEntity.getId())) {
            throw new RuntimeException("Bad Request:: Comment Not Found");
        }

        CommentDto  commentDto = convertEntityToDto(commentEntity);

        try {
            commentDto = applyPatchToComment(jsonPatch, commentDto);
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        CommentEntity patchedCommentEntity = convertDtoToEntity(commentDto);
        patchedCommentEntity.setPostEntity(postEntity);
        commentRepository.save(patchedCommentEntity);

        return commentDto;
    }

    @Override
    public String deleteCommentByPostIdAndCommentId(long postId, long commentId) {
        return null;
    }

    @Override
    public String deleteAllCommentsOfPostFromPostId(long postId) {
        //Fetching PostEntity using PostRepository from postId
        PostEntity postEntity=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",String.valueOf(postId)));
        //deleting all the comments for that particular Post
        commentRepository.deleteByPostId(postId);
        return "All the Comments for the post with Id : " + postId + " is deleted.";

    }


    private CommentEntity convertDtoToEntity(CommentDto commentDto) {
//        CommentEntity commentEntity = new CommentEntity();
//        commentEntity.setUserName(commentDto.getUserName());
//        commentEntity.setEmail(commentDto.getEmail());
//        commentEntity.setBody(commentDto.getBody());
        CommentEntity commentEntity = modelMapper.map(commentDto, CommentEntity.class);
        return commentEntity;
    }


    private CommentDto convertEntityToDto(CommentEntity commentEntity) {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(commentEntity.getId());
//        commentDto.setUserName(commentEntity.getUserName());
//        commentDto.setEmail(commentEntity.getEmail());
//        commentDto.setBody(commentEntity.getBody());
        CommentDto commentDto = modelMapper.map(commentEntity, CommentDto.class);
        return commentDto;
    }

    private CommentDto applyPatchToComment(JsonPatch jsonPatch, CommentDto commentDto) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode commentDtoJsonNode = objectMapper.convertValue(commentDto, JsonNode.class);
        JsonNode patchedJsonNode = jsonPatch.apply(commentDtoJsonNode);
        return objectMapper.treeToValue(patchedJsonNode, CommentDto.class);

    }
}
