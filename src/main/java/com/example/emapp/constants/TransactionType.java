package com.example.emapp.constants;

public enum TransactionType {
    DEPOSIT("DEPOSIT"), WITHDRAW("WITHDRAW"),TRANSFER("TRANSFER"), PAYMENT("PAYMENT");
    final String name;
    TransactionType(String name) {
        this.name = name;
    }
}
