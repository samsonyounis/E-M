package com.example.emapp.wrappers;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResponse {
    private String status;
    private String message;
    private Object data;
}
