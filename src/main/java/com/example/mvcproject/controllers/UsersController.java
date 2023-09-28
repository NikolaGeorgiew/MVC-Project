package com.example.mvcproject.controllers;

import com.example.mvcproject.dtos.UserLoginDto;
import com.example.mvcproject.dtos.UserRegisterDto;
import com.example.mvcproject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {

        return "user/login";
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@Valid UserLoginDto employeeLoginDto, BindingResult bindingResult) {
        if (employeeLoginDto.getUsername().equals("admin") &&
            employeeLoginDto.getPassword().equals("secure")) {

            ModelAndView result = new ModelAndView();
            result.setViewName("redirect:/home");
            return result;
        }


        List<String> errors = bindingResult.
                        getAllErrors().
                        stream().
                        map(e -> e.getObjectName() + " " + e.getDefaultMessage())
                        .toList();


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/login");
        modelAndView.addObject("errors", errors);


        return modelAndView;
    }

    @GetMapping("/register")
    public String register() {
        return "user/register";
    }
    @PostMapping("/register")
    public String doRegister(@Valid UserRegisterDto userRegisterDto) {
        boolean success = userService.register(userRegisterDto);

        if (success) {
            return "redirect:/users/login";
        }

        return "user/register";
    }

}
