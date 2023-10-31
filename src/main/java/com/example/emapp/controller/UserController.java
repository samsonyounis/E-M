package com.example.emapp.controller;

import com.example.emapp.service.AcountsServiceImpl;
import com.example.emapp.wrappers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AcountsServiceImpl accountsService;

    @PostMapping("/create")
    public GlobalResponse createUserAcount(@RequestBody CreateUserWrapper wrapper) throws ParseException {
       return accountsService.createUserAccount(wrapper);
    }

    @GetMapping("/getall")
    public GlobalResponse getAllUsers(){
       return accountsService.getAllUsers();
    }


    @GetMapping("/getUserById")
    public GlobalResponse getUserById(@RequestParam Long id) {

        return accountsService.getUserById(id);
    }

    @GetMapping("/getallAccounts")
    public GlobalResponse getAllAccounts(){
        return accountsService.getAllAccounts();
    }


    @PostMapping("/login")
    public GlobalResponse loginUser(@RequestBody LoginWrapper wrapper) {

        return accountsService.loginUser(wrapper);
    }

    @PostMapping("/deposit")
    public GlobalResponse deposite(@RequestBody DepositeWrapper wrapper) throws ParseException {
        return accountsService.depositCash(wrapper);
    }

    @PostMapping("/withdraw")
    public GlobalResponse withdraw(@RequestBody WithdrawWrapper wrapper) throws ParseException {
        return accountsService.withdrawCash(wrapper);
    }

    @PostMapping("/update")
    public GlobalResponse updateAccount(@RequestBody UpdateaccountWrapper wrapper) {
        return accountsService.updateAccountInfo(wrapper);
    }

    @PostMapping("/generate")
    public GlobalResponse generateBankNumber(){
        String bankNumber = UUID.randomUUID().toString();
        return GlobalResponse.builder()
                .status("200")
                .message("generate bank number")
                .data(bankNumber).build();
    }
}
