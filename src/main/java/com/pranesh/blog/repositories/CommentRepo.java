package com.pranesh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pranesh.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
