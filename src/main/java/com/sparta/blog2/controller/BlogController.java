package com.sparta.blog2.controller;

import com.sparta.blog2.dto.BlogRequestDto;
import com.sparta.blog2.dto.BlogResponseDto;
import com.sparta.blog2.dto.StatusCodeDto;
import com.sparta.blog2.security.UserDetailsImpl;
import com.sparta.blog2.service.BlogService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }

    //게시글 작성
    @PostMapping("/blog")
    public BlogResponseDto createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.createBlog(requestDto, userDetails.getUser());
    }

    //전체 게시글 조회
    @GetMapping("/blog")
    public List<BlogResponseDto> getBlog(){
        return blogService.getBlog();
    }

    //선택한 게시글 조회
    @GetMapping("/blog/{id}")
    public BlogResponseDto getBlog(@PathVariable Long id){
        return blogService.getBlog(id);
    }

    //선택한 게시글 수정
    @PutMapping("/blog/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.updateBlog(id, requestDto, userDetails.getUser());
    }

    //선택한 게시글 삭제
    @DeleteMapping("/blog/{id}")
    public StatusCodeDto deleteMyblog(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.deleteBlog(id, userDetails.getUser());
    }
}