package com.example.demo.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "cars")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {

    @Id
    private String id;

    @NotBlank
    private String coverImage;

    @NotNull
    private Integer price;

    @NotNull
    private String currency;

    @NotBlank
    private String origin;

    @NotBlank
    private String make;

    @NotBlank
    private String makeUrl;

    @NotBlank
    private String model;

    @NotNull
    private Integer year;

    @NotNull
    private Integer mileage;

    @NotBlank
    private String gear;

    @NotBlank
    private String type;

    @NotBlank
    private String engine;

    @NotBlank
    private String shape;

    @NotBlank
    private String color;

    @Builder.Default
    private String[] luxury = new String[]{};

    @Builder.Default
    private String[] safety = new String[]{};

    @NotBlank
    private String location;

    @NotBlank
    private String country;

    @Builder.Default
    private Boolean sold = false;

    private int stars = 0;

    List<String> comments = Collections.emptyList();

    private int seenCount = 0;

    private int likeCount = 0;

    private int shareCount = 0;

    private Set<String> likedBy = new HashSet<>();

    private Set<String> sharedBy = new HashSet<>();

    @Builder.Default
    private String description ="";

    @Builder.Default
    private String[] images = new String[]{};

    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
