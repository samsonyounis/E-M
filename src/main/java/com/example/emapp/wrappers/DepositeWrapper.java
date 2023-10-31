package com.example.emapp.wrappers;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositeWrapper {
    private Long userId;
    private String accountNo;
    private Long amount;
}
