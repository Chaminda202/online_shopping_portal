package com.sprintboot.product.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class APIError {
    private List<String> errors;
    private LocalDateTime timeStamp;
    private String pathUrl;
    private int statusCode;
}
