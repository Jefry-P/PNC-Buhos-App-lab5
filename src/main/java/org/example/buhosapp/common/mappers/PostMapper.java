package org.example.buhosapp.common.mappers;

import org.example.buhosapp.domain.dtos.request.post.CreatePostRequest;
import org.example.buhosapp.domain.dtos.response.post.PostResponse;
import org.example.buhosapp.domain.entities.Post;
import org.example.buhosapp.domain.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {
    public Post toEntityCreate(CreatePostRequest request, User user) {
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
    }

    public PostResponse toDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .authorName(post.getUser() != null ? post.getUser().getUsername() : null)
                .build();
    }
}
