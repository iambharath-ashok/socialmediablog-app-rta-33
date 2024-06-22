package com.bharath.learning.socialmediablog_app_rta_33.service.impl;

import com.bharath.learning.socialmediablog_app_rta_33.dto.PostDto;
import com.bharath.learning.socialmediablog_app_rta_33.excpetions.ResourceNotFoundException;
import com.bharath.learning.socialmediablog_app_rta_33.model.PostEntity;
import com.bharath.learning.socialmediablog_app_rta_33.payload.PostResponse;
import com.bharath.learning.socialmediablog_app_rta_33.repository.PostRepository;
import com.bharath.learning.socialmediablog_app_rta_33.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepository.findAll();
        //Map/ convert PostEntity to PostDto
        if(postEntities != null) {
          return postEntities.stream().map(postEntity -> mapEntityToDto(postEntity)).collect(Collectors.toList());
        }
        return null;
    }


    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<PostEntity> postEntities = postRepository.findAll(pageable);

        //Map/ convert PostEntity to PostDto
        if(postEntities != null) {
            List<PostDto> postDtoList = postEntities.stream().map(postEntity -> mapEntityToDto(postEntity)).collect(Collectors.toList());

            PostResponse postResponse =    PostResponse.builder()
                    .content(postDtoList)
                    .pageNo(postEntities.getNumber())
                    .pageSize(postEntities.getSize())
                    .totalPages(postEntities.getTotalPages())
                    .totalElements(postEntities.getTotalElements())
                    .isLastPage(postEntities.isLast())
                    .build();
            return postResponse;

        }
        return null;
    }


    @Override
    public PostDto getPostById(long postId) {
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id", String.valueOf(postId)));
        return mapEntityToDto(postEntity);
    }

    @Override
    public PostDto createPost(PostDto inPostDto) {
       PostEntity postEntity = mapDtoToEntity(inPostDto);

       PostEntity savedPostEntity = postRepository.save(postEntity);
       PostDto outPostDto =  mapEntityToDto(savedPostEntity);
       return outPostDto;
    }



    @Override
    public PostDto updatePost(PostDto postDto, long postId) {

        //find resource by Id
      PostEntity postEntityToBeUpdated =  postRepository.findById(postId).orElseThrow(() ->  new ResourceNotFoundException("Post","id", String.valueOf(postId)));

      //map the updated resource
      postEntityToBeUpdated.setTitle(postDto.getTitle());
      postEntityToBeUpdated.setDescription(postDto.getDescription());
      postEntityToBeUpdated.setContent(postDto.getContent());

      //save into database
      PostEntity updatedPostEntity = postRepository.save(postEntityToBeUpdated);

      //convert entity to dto and return the dto
      return mapEntityToDto(updatedPostEntity);
    }

    @Override
    public void deletePostById(long postId) {
        PostEntity postEntityToBeDeleted =  postRepository.findById(postId).orElseThrow(() ->  new ResourceNotFoundException("Post","id", String.valueOf(postId)));
        postRepository.delete(postEntityToBeDeleted);
    }



    private PostDto mapEntityToDto(PostEntity postEntity) {
        PostDto postDto = new PostDto();
        postDto.setId(postEntity.getId());
        postDto.setTitle(postEntity.getTitle());
        postDto.setDescription(postEntity.getDescription());
        postDto.setContent(postEntity.getContent());
        return postDto;
    }


    private PostEntity mapDtoToEntity(PostDto postDto) {
        PostEntity postEntity = new PostEntity();
        postEntity.setDescription(postDto.getDescription());
        postEntity.setTitle(postDto.getTitle());
        postEntity.setContent(postDto.getContent());
        return postEntity;
    }



}
