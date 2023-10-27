package com.example.emapp.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class securityConfig {
    @Autowired
    private userDetailsService myUserDetailsService; // instance of userDetailsService class
    @Autowired
    private webRequestFilter requestFilter;
    @Autowired
    private  AuthenticationEntryPoint authenticationEntryPoint;

    //Authentication Provider
    // instead of overriding this (//public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)in the WebSecurityConfigurerAdapter

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(myUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
//instead of overriding the AuthenticationManagerBean in the WebSecurityConfigurerAdapter.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }



    //bean to return the BcryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                //this shows that the session is stateless.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //our public endpoints.
                .authorizeHttpRequests().requestMatchers(
                        "/api/v1/users/create",
                        "/api/v1/users/login",
                        "/api/v1/users/getall",
                        "/api/v1/users/getallAccounts")
                .permitAll().requestMatchers("/api/v1/users/deposit",
                        "/api/v1/users/withdraw").
                //"/employee/add"
                hasRole("user")
                // these are the private end points.
                .anyRequest().authenticated();
        //this adds the authentication provide bean above
        http.authenticationProvider(authenticationProvider());
        //this adds the jwtTokenFilter for validation before accessing any api.
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
