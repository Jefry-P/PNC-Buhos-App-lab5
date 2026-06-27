package org.example.buhosapp.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.buhosapp.domain.dtos.request.post.CreatePostRequest;
import org.example.buhosapp.domain.dtos.request.post.UpdatePostRequest;
import org.example.buhosapp.domain.dtos.response.GeneralResponse;
import org.example.buhosapp.services.impl.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {
    private final PostServiceImpl postService;

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createPost(@RequestBody @Valid CreatePostRequest request) {
        return buildResponse(
                "Post created successfully",
                HttpStatus.CREATED,
                postService.createPost(request)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> updatePost(
            @PathVariable UUID id,
            @RequestBody @Valid UpdatePostRequest request
    ) {
        return buildResponse(
                "Post updated successfully",
                HttpStatus.OK,
                postService.updatePost(id, request)
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<GeneralResponse> getPost(@PathVariable UUID id) {
        return buildResponse(
                "Post found successfully",
                HttpStatus.OK,
                postService.getPostById(id)
        );
    }

    private ResponseEntity<GeneralResponse> buildResponse(String message, HttpStatus status, Object data) {
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity
                .status(status)
                .body(GeneralResponse.builder()
                        .uri(uri)
                        .message(message)
                        .status(status.value())
                        .time(LocalDateTime.now())
                        .data(data)
                        .build()
                );
    }
}
