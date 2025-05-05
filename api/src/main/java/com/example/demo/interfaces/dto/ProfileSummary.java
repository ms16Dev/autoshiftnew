package com.example.demo.interfaces.dto;

import java.time.LocalDateTime;

public record ProfileSummary(
        String id,
        String coverUrl,
        String avatarUrl,
        String username,
        String name,
        String subtitle,
        String location,
        int likeCount

) {

}


