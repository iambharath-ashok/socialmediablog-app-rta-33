package com.bharath.learning.socialmediablog_app_rta_33.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private long id;
    @NotEmpty(message = "Name should not be null or empty")
    private String userName;
    @Email
    @NotNull(message = "Email Should not be null or empty and it should be in proper email format")
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum of 10 Characters")
    private String body;
}
