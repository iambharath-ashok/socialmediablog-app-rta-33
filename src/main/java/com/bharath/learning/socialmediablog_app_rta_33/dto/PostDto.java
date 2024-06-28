package com.bharath.learning.socialmediablog_app_rta_33.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 5, message = "Post title should have at least 5 characters")
    private String title;
    @Size(min =10, message = "Post Description should have at least 10 characters")
    private String description;
    @NotEmpty
    private String content;

}
