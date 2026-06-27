package org.example.buhosapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.buhosapp.common.mappers.PostMapper;
import org.example.buhosapp.domain.dtos.request.post.CreatePostRequest;
import org.example.buhosapp.domain.dtos.request.post.UpdatePostRequest;
import org.example.buhosapp.domain.dtos.response.post.PostResponse;
import org.example.buhosapp.domain.entities.Post;
import org.example.buhosapp.domain.entities.User;
import org.example.buhosapp.exceptions.ResourceNotFoundException;
import org.example.buhosapp.repositories.PostRepository;
import org.example.buhosapp.repositories.UserRepository;
import org.example.buhosapp.services.PostService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Override
    public PostResponse createPost(CreatePostRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getUserId()));
        
        Post post = postMapper.toEntityCreate(request, user);
        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public PostResponse updatePost(UUID id, UpdatePostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        
        return postMapper.toDto(postRepository.save(post));
    }

    @Override
    public PostResponse getPostById(UUID id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
        return postMapper.toDto(post);
    }
}
