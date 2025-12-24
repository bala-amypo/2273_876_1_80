package com.example.demo.dto;

public class JwtResponse {

    private String token;
    private String type = "Bearer";

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
 
    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }
}
