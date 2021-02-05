package com.spring.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SignUpDto {
    private String loginId;
    private String password;
    private String accessLevel;
}
