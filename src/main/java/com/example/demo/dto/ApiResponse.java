package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
Map<String, Object> responseData = new HashMap<>();
responseData.put("token", token);
responseData.put("user", user);

return ResponseEntity.ok(new ApiResponse(true, "Login successful", responseData));

public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;  // can hold Map or any object

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
