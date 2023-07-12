package com.sparta.blog2.service;

import com.sparta.blog2.dto.BlogRequestDto;
import com.sparta.blog2.dto.BlogResponseDto;
import com.sparta.blog2.dto.StatusCodeDto;
import com.sparta.blog2.entity.Blog;
import com.sparta.blog2.entity.User;
import com.sparta.blog2.entity.UserRoleEnum;
import com.sparta.blog2.jwt.JwtUtil;
import com.sparta.blog2.repository.BlogRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final JwtUtil jwtUtil;

    public BlogService(BlogRepository blogRepository, JwtUtil jwtUtil){
        this.blogRepository = blogRepository;
        this.jwtUtil = jwtUtil;
    }

    //게시글 작성
    public BlogResponseDto createBlog(BlogRequestDto requestDto, User user) {

            //RequestDto -> Entity
            Blog blog = new Blog(requestDto, user);

            //DB 저장
            Blog saveBlog = blogRepository.save(blog);

            //Entity -> ResponseDto
            BlogResponseDto blogResponseDto = new BlogResponseDto(saveBlog);

            return blogResponseDto;
        }

    //전체 게시글 조회
    public List<BlogResponseDto> getBlog() {
        //DB 조회
        return blogRepository.findAllByOrderByModifiedAtDesc().stream().map(BlogResponseDto::new).toList();
    }

    //게시글 수정
    @Transactional
    public BlogResponseDto updateBlog(Long id, BlogRequestDto requestDto, User user) {
        //해당 글이 DB에 존재하는지 확인
        Blog blog = findBlog(id);

        if(!(user.getUsername().equals((blog.getUser().getUsername())) || UserRoleEnum.ADMIN.equals(user.getRole()))){
            throw new IllegalArgumentException("작성자만 수정가능합니다.");
        }
        //글 내용 수정
        blog.update(requestDto);

        return new BlogResponseDto(blog);

    }

    //게시글 삭제하기
    public StatusCodeDto deleteBlog(Long id,  User user) {
        //해당 글이 DB에 존재하는지 확인
        Blog blog = findBlog(id);

        if(!(user.getUsername().equals((blog.getUser().getUsername())) || UserRoleEnum.ADMIN.equals(user.getRole()))){
            throw new IllegalArgumentException("작성자만 삭제가능합니다.");
        }

        blogRepository.delete(blog);

        return new StatusCodeDto("삭제 성공", HttpStatus.OK.value());
    }

    //선택한 게시글 조회
    public BlogResponseDto getBlog(Long id) {
        //해당 글이 DB에 존재하는지 확인
        Blog blog = findBlog(id);

        BlogResponseDto blogResponseDto = new BlogResponseDto(blog);

        return blogResponseDto;
    }

    private Blog findBlog(Long id){
        Blog blog = blogRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다."));

        return blog;
    }
}