package com.example.emapp.wrappers;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepositeWrapper {
    private Long userId;
    private Long accountNo;
    private Long amount;
}
