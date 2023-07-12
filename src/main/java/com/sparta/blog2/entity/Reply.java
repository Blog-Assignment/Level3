package com.sparta.blog2.entity;

import com.sparta.blog2.dto.ReplyRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reply")
public class Reply extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    public Reply(ReplyRequestDto requestDto, User user, Blog blog) {
        this.contents = requestDto.getContents();
        this.user = user;
        this.blog = blog;
    }

    public void update(ReplyRequestDto requestDto) {
        this.contents = requestDto.getContents();
    }
}
