package com.spring.security.jwt.dto;

import lombok.Data;

@Data
public class JwtTokenDto {
    private String jwt;

    public JwtTokenDto(String jwt) {
        this.jwt = jwt;
    }
}
