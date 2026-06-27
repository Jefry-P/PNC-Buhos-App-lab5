package org.example.buhosapp.services;

import org.example.buhosapp.domain.dtos.request.post.CreatePostRequest;
import org.example.buhosapp.domain.dtos.request.post.UpdatePostRequest;
import org.example.buhosapp.domain.dtos.response.post.PostResponse;

import java.util.UUID;

public interface PostService {
    PostResponse createPost(CreatePostRequest request);
    PostResponse updatePost(UUID id, UpdatePostRequest request);
    PostResponse getPostById(UUID id);
}
