package com.example.emapp.models;


import com.example.emapp.constants.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "transactions_tbl")
public class Transactionss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Africa/Nairobi")
    private Date dateOfTransaction;
    private Long accountNo;
    @Enumerated(EnumType.STRING)
    private TransactionType typeOfTransaction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;
}
