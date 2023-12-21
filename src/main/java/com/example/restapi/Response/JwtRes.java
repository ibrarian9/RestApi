package com.example.restapi.Response;

import lombok.Getter;

@Getter
public class JwtRes {

  private String token;
  private int id;
  private String username;
  private String email;

    public JwtRes(String token, int id, String username, String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
