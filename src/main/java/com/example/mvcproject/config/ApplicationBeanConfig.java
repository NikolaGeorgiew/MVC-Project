package com.example.mvcproject.config;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfig {


    @Bean
    public ModelMapper createModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //dto is String, class LocalDate
        modelMapper.addConverter(mappingContext -> LocalDate.parse(mappingContext.getSource(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        String.class,
        LocalDate.class);


        return modelMapper;
    }
}
