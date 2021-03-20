package com.example.portfolio.service;


import com.example.portfolio.dto.PostRequest;
import com.example.portfolio.dto.PostResponse;
import com.example.portfolio.exception.FiteNotFoundException;
import com.example.portfolio.exception.PostNotFoundException;
import com.example.portfolio.mapper.PostMapper;
import com.example.portfolio.model.Post;
import com.example.portfolio.model.Subfite;
import com.example.portfolio.model.User;
import com.example.portfolio.repository.PostRepository;
import com.example.portfolio.repository.SubfiteRepository;
import com.example.portfolio.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final SubfiteRepository subfiteRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;


    public void save(PostRequest postRequest){
        System.out.println("-----------------------------------------------");
        System.out.println(postRequest.getSubfiteName());
        System.out.println("-----------------------------------------------");

        Subfite subfite = subfiteRepository.findByName(postRequest.getSubfiteName())
                .orElseThrow(() -> new FiteNotFoundException(postRequest.getSubfiteName()));
        postRepository.save(postMapper.map(postRequest, subfite, authService.getCurrentUser()));
    }


    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post post =postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubfite(Long subfiteId){
        Subfite subfite= subfiteRepository.findById(subfiteId)
                .orElseThrow(() -> new FiteNotFoundException(subfiteId.toString()));

        List<Post> posts = postRepository.findAllBySubfite(subfite);
        return posts
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

}
