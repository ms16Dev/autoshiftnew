/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;


@Document(collection = "users")
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Allows to write (set) but not read
    private String password;

    @Email
    private String email;

    @Builder.Default()
    private boolean active = false;

    @Builder.Default()
    private boolean verified = false;

    @Builder.Default()
    private List<String> roles = Collections.emptyList();

}
