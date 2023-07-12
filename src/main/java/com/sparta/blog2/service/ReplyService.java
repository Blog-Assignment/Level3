package com.sparta.blog2.service;

import com.sparta.blog2.dto.ReplyRequestDto;
import com.sparta.blog2.dto.ReplyResponseDto;
import com.sparta.blog2.entity.Blog;
import com.sparta.blog2.entity.Reply;
import com.sparta.blog2.entity.User;
import com.sparta.blog2.entity.UserRoleEnum;
import com.sparta.blog2.repository.BlogRepository;
import com.sparta.blog2.repository.ReplyRepository;
import com.sparta.blog2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final ReplyRepository replyRepository;

    public ReplyService(UserRepository userRepository, BlogRepository blogRepository, ReplyRepository replyRepository) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
    }

//    //댓글 조회
//    public List<ReplyResponseDto> getReply(){
//        return replyRepository.findAllByOrderByModifiedAtDesc().stream().map(ReplyResponseDto::new).toList();
//    }

    //댓글 작성
    public ReplyResponseDto createReply(ReplyRequestDto requestDto, User user){
        Blog blog = blogRepository.findById(requestDto.getBlogId()).orElseThrow(
                ()->new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Reply reply = replyRepository.save(new Reply(requestDto, user, blog));
        return new ReplyResponseDto(reply);
    }


    //댓글 수정
    @Transactional
    public ReplyResponseDto updateReply(Long id, ReplyRequestDto requestDto, User user) {
        //해당 댓글이 DB에 존재하는지 확인
        Reply reply = replyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!(user.getUsername().equals((reply.getUser().getUsername())) || UserRoleEnum.ADMIN.equals(user.getRole()))){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        reply.update(requestDto);
        return new ReplyResponseDto(reply);
    }

    //댓글 삭제
    @Transactional
    public ReplyResponseDto deleteReply(Long id, User user) {
        //해당 댓글이 DB에 존재하는지 확인
        Reply reply = replyRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if(!(user.getUsername().equals((reply.getUser().getUsername())) || UserRoleEnum.ADMIN.equals(user.getRole()))){
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        replyRepository.delete(reply);
        return new ReplyResponseDto(reply);
    }

//    public ReplyResponseDto getReply(Long id, User user) {
//        UserRoleEnum userRoleEnum = user.getRole();
//
//        List<Reply> replyList;
//
//        if(userRoleEnum == UserRoleEnum.USER){
//            replyList = replyRepository.findByReplyId();
//        } else {
//            replyList = replyRepository.findAll();
//        }
//        return replyList;
//    }
}
