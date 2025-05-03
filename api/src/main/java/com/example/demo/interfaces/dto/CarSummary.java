package com.example.demo.interfaces.dto;

import java.time.LocalDateTime;

public record CarSummary(
        String id,
        String coverImage,
        int price,
        String currency,
        String make,
        String makeUrl,
        String model,
        String origin,
        int year,
        int seenCount,
        int likeCount,
        int shareCount,
        int commentCount,
        String description,
        String createdBy,
        LocalDateTime createdDate
) {

}


