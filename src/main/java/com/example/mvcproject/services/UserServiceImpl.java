package com.example.mvcproject.services;

import com.example.mvcproject.dtos.UserRegisterDto;
import com.example.mvcproject.entities.User;
import com.example.mvcproject.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean register(UserRegisterDto userRegisterDto) {



        // Create user entity
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRegisterDto, User.class);


        //Ensure email is free
        boolean emailInUse = userRepository.existsByEmail(userRegisterDto.getEmail());
        if (emailInUse) {
            return false;
        }

        //Save user entity
        userRepository.save(user);
        return true;
    }
}
