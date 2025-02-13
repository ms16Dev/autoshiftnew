package com.example.demo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "stats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stats implements Serializable {

    @Id
    private String id;

    @NotBlank
    private int views;

   @NotBlank
    private int likes;

   @NotBlank
    private int shares;

   @NotBlank
    private int comments;

}
