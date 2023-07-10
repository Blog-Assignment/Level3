package com.sparta.blog2.controller;

import com.sparta.blog2.dto.SignupRequestDto;
import com.sparta.blog2.dto.StatusCodeDto;
import com.sparta.blog2.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/login-page")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/user/signup")
    public StatusCodeDto signup(@RequestBody @Valid SignupRequestDto requestDto){
        userService.signup(requestDto);
        return new StatusCodeDto("회원가입 성공", HttpStatus.OK.value());
    }

//    @PostMapping("/user/login")
//    public StatusCodeDto login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res){
//        try {
//            userService.login(requestDto, res);
//        } catch (Exception e) {
//            System.out.println("로그인 실패");
//        }
//        return new StatusCodeDto("로그인 성공", HttpStatus.OK.value());
//    }
}
