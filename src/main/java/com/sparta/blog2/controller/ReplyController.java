package com.sparta.blog2.controller;

import com.sparta.blog2.dto.ReplyRequestDto;
import com.sparta.blog2.dto.ReplyResponseDto;
import com.sparta.blog2.entity.UserRoleEnum;
import com.sparta.blog2.security.UserDetailsImpl;
import com.sparta.blog2.service.ReplyService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }
//    //댓글 조회
//    @GetMapping("/replies")
//    public List<ReplyResponseDto> getReply(){
//        return replyService.getReply();
//    }

    //댓글 작성
    @PostMapping("/replies")
    public ReplyResponseDto createReply(@RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return replyService.createReply(requestDto, userDetails.getUser());
    }

    //댓글 수정
    @PutMapping("/replies/{id}")
    public ReplyResponseDto updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return replyService.updateReply(id, requestDto, userDetails.getUser());
    }


    //댓글 삭제
    @DeleteMapping("/replies/{id}")
    public ReplyResponseDto deleteReply(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return replyService.deleteReply(id, userDetails.getUser());
    }






//    @Secured(UserRoleEnum.Authority.ADMIN)
//    @GetMapping("/secured")
//    public String getProductsByAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
//        for (GrantedAuthority authority : userDetails.getAuthorities()) {
//            System.out.println("authority.getAuthority() = " + authority.getAuthority());
//        }
//        return "redirect:/";
//    }
}
