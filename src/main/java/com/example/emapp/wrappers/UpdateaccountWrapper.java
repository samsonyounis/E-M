package com.example.emapp.wrappers;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateaccountWrapper {
    private Long userId;
    private String email;
    private String password;
}
