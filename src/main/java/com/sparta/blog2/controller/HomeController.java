package com.sparta.blog2.controller;

import com.sparta.blog2.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){
        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }
}

//메인페이지로 가기 위한 controller(지금은 필요없음)
