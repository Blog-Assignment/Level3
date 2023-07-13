package com.sparta.blog2.controller;

import com.sparta.blog2.dto.SignupRequestDto;
import com.sparta.blog2.dto.StatusCodeDto;
import com.sparta.blog2.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login-page")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public StatusCodeDto signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult, HttpServletResponse httpServletResponse){
        //Validation 예외처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if(fieldErrors.size()>0){
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    log.error(fieldError.getField()+"필드 : "+fieldError.getDefaultMessage());
                }
                httpServletResponse.setStatus(400);
                return new StatusCodeDto("회원가입 실패", httpServletResponse.getStatus());
            }
            userService.signup(requestDto);
            return new StatusCodeDto("회원가입 성공", httpServletResponse.getStatus());
        }
}
