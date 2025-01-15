package com.proje.billing_site.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Email is required")
    private String mail;

    @NotBlank(message = "Password is required")
    private String password;
}

