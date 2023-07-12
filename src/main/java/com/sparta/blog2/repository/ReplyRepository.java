package com.sparta.blog2.repository;

import com.sparta.blog2.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
//    List<Reply> findAllByOrderByModifiedAtDesc();
}
