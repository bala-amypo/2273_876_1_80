package com.example.demo.dto;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;
    private String department;

    public RegisterRequest() {}

    public RegisterRequest(String name, String email, String password, String role, String department) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.department = department;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
    public String getDepartment() { return department; }
}
