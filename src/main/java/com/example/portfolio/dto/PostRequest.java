package com.example.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostRequest {

    private Long postId;
    private String subfiteName;
    private String postName;
    private String url;
    private String description;

}
