package com.example.restapi;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityExcpetion(Exception e){
        ProblemDetail problemDetail = null;

        e.printStackTrace();

        if (e instanceof BadCredentialsException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), e.getMessage());
            problemDetail.setProperty("desc", "The username or password is incorrect");
        }
        if (e instanceof AccountStatusException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            problemDetail.setProperty("desc", "The account is locked");
        }
        if (e instanceof AccessDeniedException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            problemDetail.setProperty("desc", "You are not authorized to access this resource");
        }
        if (e instanceof SignatureException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            problemDetail.setProperty("desc", "The JWT signature is invalid");
        }
        if (e instanceof ExpiredJwtException){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), e.getMessage());
            problemDetail.setProperty("desc", "The JWT token has expired");
        }
        if (problemDetail == null){
            problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getMessage());
            problemDetail.setProperty("desc", "Unknown internal server error.");
        }
        return problemDetail;
    }
}
