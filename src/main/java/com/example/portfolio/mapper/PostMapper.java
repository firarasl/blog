package com.example.portfolio.mapper;

import com.example.portfolio.dto.PostRequest;
import com.example.portfolio.dto.PostResponse;
import com.example.portfolio.model.*;
import com.example.portfolio.repository.CommentRepository;
import com.example.portfolio.repository.VoteRepository;
import com.example.portfolio.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import java.util.Optional;

import static com.example.portfolio.model.VoteType.DOWNVOTE;
import static com.example.portfolio.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")

public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subfite", source = "subfite")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, Subfite subfite, User user);

        @Mapping(target = "id", source = "postId")
        @Mapping(target = "subfiteName", source = "subfite.name")
        @Mapping(target = "username", source = "user.username")
        @Mapping(target = "commentCount", expression = "java(commentCount(post))")
        @Mapping(target = "duration", expression = "java(getDuration(post))")
        @Mapping(target = "upvote", expression = "java(isPostUpVoted(post))")
        @Mapping(target = "downvote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
