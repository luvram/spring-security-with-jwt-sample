package com.spring.security.jwt;

import lombok.Data;

@Data
public class JwtToken {
    private String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }
}
