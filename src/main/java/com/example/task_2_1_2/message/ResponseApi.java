package com.example.task_2_1_2.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseApi {
    private String message;
    private boolean success;
    private Object object;

    public ResponseApi(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
