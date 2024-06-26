package com.bharath.learning.socialmediablog_app_rta_33.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenericConfiguaration {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
