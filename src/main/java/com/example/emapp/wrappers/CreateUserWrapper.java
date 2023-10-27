package com.example.emapp.wrappers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserWrapper {
    @NotNull(message = "First name can not be null")
    private String firstName;
    private String lastName;
    private String surName;
    @NotBlank(message = "the id must not be black")
    private int nationalId;
    @NotNull(message = "Email can not be null")
    private String email;
    private String password;
    private String roles;
}
