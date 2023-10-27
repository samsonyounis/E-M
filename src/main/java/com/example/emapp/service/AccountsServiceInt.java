package com.example.emapp.service;

import com.example.emapp.models.Users;
import com.example.emapp.wrappers.*;

import java.text.ParseException;

public interface AccountsServiceInt {

    GlobalResponse createUserAccount(CreateUserWrapper wrapper) throws ParseException;

    GlobalResponse getAllUsers();

    GlobalResponse getAllAccounts();

    GlobalResponse loginUser(LoginWrapper wrapper);

    GlobalResponse depositCash(DepositeWrapper wrapper) throws ParseException;

    GlobalResponse withdrawCash(WithdrawWrapper wrapper) throws ParseException;

    GlobalResponse updateAccountInfo(UpdateaccountWrapper wrapper);
}
