package com.example.emapp.wrappers;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginWrapper {
    private String email;
    private String password;
}
