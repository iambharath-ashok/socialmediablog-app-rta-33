package com.bharath.learning.socialmediablog_app_rta_33.excpetions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String fieldValue;


    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
//        super(resourceName +" not found with "+ fieldName + " with value "+ fieldValue);
        super(String.format("%s not found with %s :: %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
