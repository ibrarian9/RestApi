package com.example.restapi.Response;

import lombok.Getter;

@Getter
public class LoginRes {

    private String auth;
    private String username;
    private String token;

    public LoginRes(String auth, String username, String token) {
        this.auth = auth;
        this.username = username;
        this.token = token;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
