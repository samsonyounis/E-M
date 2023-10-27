package com.example.emapp.service;

import com.example.emapp.constants.TransactionType;
import com.example.emapp.models.Accounts;
import com.example.emapp.models.Transactionss;
import com.example.emapp.models.Users;
import com.example.emapp.repository.AccountsRepository;
import com.example.emapp.repository.TransactionsRepository;
import com.example.emapp.repository.UsersRepository;
import com.example.emapp.securityConfig.jwtUtility;
import com.example.emapp.securityConfig.userDetailsService;
import com.example.emapp.wrappers.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
public class AcountsServiceImpl implements AccountsServiceInt{
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountsRepository accountsRepository;
    private final userDetailsService userDetailsService1;
    private final jwtUtility jwtUtility1;
    private final TransactionsRepository transactionsRepository;
    @Override
    public GlobalResponse createUserAccount(CreateUserWrapper wrapper) throws ParseException {
        //build user object
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdOn = dateformat.parse(dateformat.format(new Date()));
        Random rand = new Random();

        Long no = rand.nextLong(16);
        log.info("the accuont no is"+" "+no);

        //build user object
        Users user = Users.builder().email(wrapper.getEmail()).password(passwordEncoder.encode(wrapper.getPassword()))
                .roles(wrapper.getRoles()).firstName(wrapper.getFirstName())
                .lastName(wrapper.getLastName()).nationalId(wrapper.getNationalId())
                .surName(wrapper.getSurName())
                .dateOfCreation(createdOn)
                .build();

        //build account object
        Accounts account = Accounts.builder()
                .accountName(user.getFirstName()+" "+user.getLastName())
                .accountNo(no)
                .dateCreated(createdOn)
                .balance(0L)
                .user(user)
                .build();

        //create user and create account
        usersRepository.save(user);
        accountsRepository.save(account);
        Users savedUser = usersRepository.findUserByEmail(wrapper.getEmail()).orElse(null);
        log.info("saved user account "+" "+ savedUser.getAccountsList());
        return GlobalResponse.builder()
                .status("200")
                .message("user created successfully")
                .data(savedUser)
                .build();
    }

    @Override
    public GlobalResponse getAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        GlobalResponse response = GlobalResponse.builder()
                .status("200")
                .message("Users found")
                .data(usersList).build();
        return response;
    }

    @Override
    public GlobalResponse getAllAccounts() {
        List<Accounts> accountsList = accountsRepository.findAll();
        GlobalResponse response = GlobalResponse.builder()
                .status("200")
                .message("Accounts found")
                .data(accountsList).build();
        return response;
    }

    @Override
    public GlobalResponse loginUser(LoginWrapper wrapper) {

        //get user from the database
        Optional<Users> optionalUser = usersRepository.findUserByEmail(wrapper.getEmail());
        if (optionalUser.isEmpty()) {
            log.info("User not found");
            return GlobalResponse.builder()
                    .status("00")
                    .message("Login Failed")
                    .data("Invalid credentials").build();
        }
        Users user = optionalUser.get();
        //check if the password match and generate token and refreshtoken
        //check also the account active, login attempts and other parameters

        if (passwordEncoder.matches(wrapper.getPassword(), user.getPassword())) {
            log.info("User credentials were correct and login was successfull");
            final UserDetails userDetails = userDetailsService1.loadUserByUsername(wrapper.getEmail());

            String accessToken = jwtUtility1.generateJwtToken(userDetails);

                return GlobalResponse.builder()
                        .status("01")
                        .message("Login Successful")
                        .data(accessToken)
                        .build();
            }
            return GlobalResponse.builder()
                    .status("01")
                    .message("Login failed")
                    .build();


    }

    @Override
    public GlobalResponse depositCash(DepositeWrapper wrapper) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Optional<Users> optionalUser = usersRepository.findById(wrapper.getUserId());
        if (optionalUser.isEmpty()){
            return GlobalResponse.builder()
                    .status("400")
                    .message("user not found")
                    .build();
        }
        Users user = optionalUser.get();
        List<Accounts> account = user.getAccountsList().stream().filter(a -> Objects.equals(a.getAccountNo(), wrapper.getAccountNo())).toList();
        log.info("the user account number is "+" "+ account);
        if (account.isEmpty()){
            return GlobalResponse.builder()
                    .status("400")
                    .message("Account not matching")
                    .build();
        }
        Accounts userAccount = account.get(0);
        Long newBalance = userAccount.getBalance() + wrapper.getAmount();
        userAccount.setBalance(newBalance);
        accountsRepository.save(userAccount);
        //build transaction object/entity
        Date createdOn = dateformat.parse(dateformat.format(new Date()));
        Transactionss transaction = Transactionss.builder()
                .user(user)
                .dateOfTransaction(createdOn)
                .accountNo(userAccount.getAccountNo())
                .typeOfTransaction(TransactionType.DEPOSIT).build();
        //saving the transaction
        transactionsRepository.save(transaction);
        return GlobalResponse.builder()
                .status("200").message("Amount deposited successfully").build();
    }

    @Override
    public GlobalResponse withdrawCash(WithdrawWrapper wrapper) throws ParseException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Optional<Users> optionalUser = usersRepository.findById(wrapper.getUserId());
        if (optionalUser.isEmpty()){
            return GlobalResponse.builder()
                    .status("400")
                    .message("user not found")
                    .build();
        }
        Users user = optionalUser.get();
        List<Accounts> account = user.getAccountsList().stream().filter(a -> a.getAccountNo() == wrapper.getAccountNo()).toList();
        log.info("the user account number is "+" "+ account);
        if (account.isEmpty()){
            return GlobalResponse.builder()
                    .status("400")
                    .message("Account not matching")
                    .build();
        }
        Accounts userAccount = account.get(0);
        if (userAccount.getBalance() < wrapper.getAmount()){
            return GlobalResponse.builder().status("400")
                    .message("Account balance is low").build();
        }
        Long newBalance = userAccount.getBalance() - wrapper.getAmount();
        userAccount.setBalance(newBalance);
        accountsRepository.save(userAccount);

        //build transaction object/entity
        Date createdOn = dateformat.parse(dateformat.format(new Date()));
        Transactionss transaction = Transactionss.builder()
                .user(user)
                .dateOfTransaction(createdOn)
                .accountNo(userAccount.getAccountNo())
                .typeOfTransaction(TransactionType.WITHDRAW).build();
        //saving the transaction
        transactionsRepository.save(transaction);
        return GlobalResponse.builder()
                .status("200").message("Successfull withdraw").build();
    }

    @Override
    public GlobalResponse updateAccountInfo(UpdateaccountWrapper wrapper) {
        Optional<Users> optionalUser = usersRepository.findById(wrapper.getUserId());
        if (optionalUser.isEmpty()){
            return GlobalResponse.builder()
                    .status("400")
                    .message("user not found")
                    .build();
        }
        Users user = optionalUser.get();
        user.setEmail(wrapper.getEmail());
        user.setPassword(passwordEncoder.encode(wrapper.getPassword()));
        Users savedUser = usersRepository.save(user);
        return GlobalResponse.builder().status("200")
                .message("User info updated successfully")
                .data(savedUser).build();
    }
}
