package org.example.buhosapp.domain.dtos.response.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class PostResponse {
    private UUID id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorName;
}
