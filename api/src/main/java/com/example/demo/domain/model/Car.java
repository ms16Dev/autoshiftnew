package com.example.demo.domain.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    @NotBlank
    private String origin;

    @NotBlank
    private String make;

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

    @Builder.Default
    private String[] location = new String[]{};

    @Builder.Default
    private Boolean sold = false;

    @Builder.Default
    private Integer stars = 0;

    @DocumentReference
    @Builder.Default
    List<Stats> stats = Collections.emptyList();

    @DocumentReference
    @Builder.Default
    List<Comment> comments = Collections.emptyList();

    private String description;

    @Builder.Default
    private String[] images = new String[]{};

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @LastModifiedBy
    private String lastModifiedBy;
}
