package com.example.portfolio.service;

import com.example.portfolio.dto.VoteDto;
import com.example.portfolio.exception.FiteException;
import com.example.portfolio.exception.PostNotFoundException;
import com.example.portfolio.model.Post;
import com.example.portfolio.model.Vote;
import com.example.portfolio.repository.PostRepository;
import com.example.portfolio.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.portfolio.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor

public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId())
            .orElseThrow(() -> new PostNotFoundException("" +
                    "Post not found id= " + voteDto.getPostId().toString()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(
                post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() &&
        voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw  new FiteException("you have alrdy "+
                    voteDto.getVoteType()+"'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount()+1);
        }else{
            post.setVoteCount(post.getVoteCount()-1);
        }

        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
