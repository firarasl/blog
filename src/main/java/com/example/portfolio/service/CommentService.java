package com.example.portfolio.service;


import antlr.LexerSharedInputState;
import com.example.portfolio.dto.CommentsDto;
import com.example.portfolio.exception.PostNotFoundException;
import com.example.portfolio.mapper.CommentMapper;
import com.example.portfolio.model.Comment;
import com.example.portfolio.model.NotificationEmail;
import com.example.portfolio.model.Post;
import com.example.portfolio.model.User;
import com.example.portfolio.repository.CommentRepository;
import com.example.portfolio.repository.PostRepository;
import com.example.portfolio.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));


        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String msg = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post "+ POST_URL);
        sendCommentNotification(msg, post.getUser());
    }

    private void sendCommentNotification(String msg, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Comment on your post ", user.getEmail(), msg));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new PostNotFoundException(postId.toString()));

        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());

    }

}












