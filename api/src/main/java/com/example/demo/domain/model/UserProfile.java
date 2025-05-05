package com.example.demo.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Document(collection = "profiles")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    private String id;

    @NotBlank
    private String username;

    @Builder.Default()
    private boolean complete = false;

    @Builder.Default()
    private boolean dealer = false;

    @Builder.Default()
    private String coverUrl = "";

    @Builder.Default()
    private String avatarUrl = "";

    @Builder.Default()
    private String name = "";

    @Builder.Default()
    private String subtitle = "";

    @Builder.Default()
    private String info = "";

    @Builder.Default()
    private String country = "";

    @Builder.Default()
    private String location = "";

    @Builder.Default()
    private List<String> contact = Collections.emptyList();

    @Builder.Default()
    private int likeCount = 0;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
