package com.example.emapp.wrappers;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawWrapper {
    private Long userId;
    private Long accountNo;
    private Long amount;
}
