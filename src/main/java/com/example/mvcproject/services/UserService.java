package com.example.mvcproject.services;

import com.example.mvcproject.dtos.UserRegisterDto;

public interface UserService {
    boolean register(UserRegisterDto userRegisterDto);
}
