package com.ustream_web3.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTO {
    private String videoUrl;

    private String videoName;

    private LocalDateTime uploadDate;

    private List<String> likes;

    private List<Map<String, String>> comments;

    private String description;

    private String username;
}