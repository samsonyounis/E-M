package com.example.emapp.ExceptionHandle;

import com.example.emapp.securityConfig.NoSuchUserExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@ControllerAdvice
public class GlobalRestApiExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable
            (HttpMessageNotReadableException ex, HttpHeaders headers,
             HttpStatusCode status, WebRequest request) {
        //return super.handleHttpMessageNotReadable(ex, headers, status, request);
        return ResponseEntity.badRequest().
                body(new CustomApiErrorResponse(status.value(),"Invalid details",ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomApiErrorResponse handleEntityNotFoundException(EntityNotFoundException ex){
        return new CustomApiErrorResponse
                (HttpStatus.NOT_FOUND.value(), "Entity not found",ex.getMessage());
    }

    @ExceptionHandler(NoSuchUserExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomApiErrorResponse handleUserNotFoundException(NoSuchUserExistsException ex){
        return new CustomApiErrorResponse
                (HttpStatus.NOT_FOUND.value(), ex.getMessg(),ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomApiErrorResponse handleException(BadCredentialsException ex){
        return new CustomApiErrorResponse
                (HttpStatus.BAD_REQUEST.value(), "Invalid username or password",ex.getMessage());
    }



    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CustomApiErrorResponse handleAuthenticationException(Exception ex){
        return new CustomApiErrorResponse
                (HttpStatus.NOT_FOUND.value(), "invalid",ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomApiErrorResponse handleMethodInvalidArgsException(MethodArgumentNotValidException ex){

        return new CustomApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "invalid Args",
                ex.getMessage());
    }
}



