package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;  // can hold Map or any object

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
