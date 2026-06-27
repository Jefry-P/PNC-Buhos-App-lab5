package org.example.buhosapp.services.impl;

import org.example.buhosapp.common.mappers.PostMapper;
import org.example.buhosapp.domain.dtos.request.post.CreatePostRequest;
import org.example.buhosapp.domain.dtos.request.post.UpdatePostRequest;
import org.example.buhosapp.domain.dtos.response.post.PostResponse;
import org.example.buhosapp.domain.entities.Post;
import org.example.buhosapp.domain.entities.User;
import org.example.buhosapp.exceptions.ResourceNotFoundException;
import org.example.buhosapp.repositories.PostRepository;
import org.example.buhosapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostServiceImpl postService;

    private UUID userId;
    private UUID postId;
    private User user;
    private Post post;
    private CreatePostRequest createRequest;
    private UpdatePostRequest updateRequest;
    private PostResponse postResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        postId = UUID.randomUUID();

        user = User.builder()
                .id(userId)
                .username("testuser")
                .email("test@example.com")
                .build();

        createRequest = CreatePostRequest.builder()
                .title("Test Post")
                .content("This is a test post content")
                .userId(userId)
                .build();

        updateRequest = UpdatePostRequest.builder()
                .title("Updated Title")
                .content("Updated content")
                .build();

        post = Post.builder()
                .id(postId)
                .title(createRequest.getTitle())
                .content(createRequest.getContent())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        postResponse = PostResponse.builder()
                .id(postId)
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .authorName(user.getUsername())
                .build();
    }

    @Test
    void createPost_shouldSavePostSuccessfully() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postMapper.toEntityCreate(createRequest, user)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        when(postMapper.toDto(post)).thenReturn(postResponse);

        PostResponse result = postService.createPost(createRequest);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(createRequest.getTitle());
        assertThat(result.getAuthorName()).isEqualTo(user.getUsername());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void createPost_shouldThrowResourceNotFoundException_whenUserDoesNotExist() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.createPost(createRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not found with id: " + userId);

        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void updatePost_shouldUpdatePostSuccessfully() {
        Post updatedPost = Post.builder()
                .id(postId)
                .title(updateRequest.getTitle())
                .content(updateRequest.getContent())
                .createdAt(post.getCreatedAt())
                .user(user)
                .build();

        PostResponse updatedResponse = PostResponse.builder()
                .id(postId)
                .title(updateRequest.getTitle())
                .content(updateRequest.getContent())
                .createdAt(post.getCreatedAt())
                .authorName(user.getUsername())
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(updatedPost);
        when(postMapper.toDto(updatedPost)).thenReturn(updatedResponse);

        PostResponse result = postService.updatePost(postId, updateRequest);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(updateRequest.getTitle());
        assertThat(result.getContent()).isEqualTo(updateRequest.getContent());
        verify(postRepository, times(1)).save(post);
    }

    @Test
    void updatePost_shouldThrowResourceNotFoundException_whenPostDoesNotExist() {
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.updatePost(postId, updateRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + postId);

        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void getPostById_shouldReturnPost_whenPostExists() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postMapper.toDto(post)).thenReturn(postResponse);

        PostResponse result = postService.getPostById(postId);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(postId);
        assertThat(result.getTitle()).isEqualTo(post.getTitle());
    }

    @Test
    void getPostById_shouldThrowResourceNotFoundException_whenPostDoesNotExist() {
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postService.getPostById(postId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post not found with id: " + postId);
    }
}
