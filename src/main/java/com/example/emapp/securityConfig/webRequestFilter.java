package com.example.emapp.securityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
class webRequestFilter extends OncePerRequestFilter {
    @Autowired
    private jwtUtility jwtUtility;
    @Autowired
    private userDetailsService myUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        log.info("the header is "+" "+authorizationHeader);
        String username = null;
        String  jwtToken = null;
        // if authorization header is not null and starts with the word bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            jwtToken = authorizationHeader.substring(7); // extracting the token
            log.info("teh token is"+" "+jwtToken);
            username = jwtUtility.getUsernameFromToken(jwtToken);//extracting the username from token.
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);//loading users
            //validating the token here by calling the validateToken function
            if (jwtUtility.validateJwtToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken UnpasswordAuthToken = new UsernamePasswordAuthenticationToken
                        (userDetails,null, userDetails.getAuthorities());
                UnpasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(UnpasswordAuthToken);
                System.out.println("Setting the security context holder");
            }
        }
        //proceeding to httpSecurity filter chain
        filterChain.doFilter(request,response);

    }
}
