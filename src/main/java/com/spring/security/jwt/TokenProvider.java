package com.spring.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider implements InitializingBean {
    private static final String ACCESS_LEVEL = "alv";

    private String base64Secret;
    private Long tokenValidityInSeconds;

    public TokenProvider(
            @Value("${jwt.base64-secret}") String base64Secret,
            @Value("${jwt.token-validity-in-seconds}") Long tokenValidityInSeconds
    ) {
        this.base64Secret = base64Secret;
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }

    private Key key;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    String createToken(Authentication authentication) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + tokenValidityInSeconds);
        if (!(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return "";
        }
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .claim(ACCESS_LEVEL, principal.getAccessLevel())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        String accessLevel = (String) claims.get(ACCESS_LEVEL);

        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(accessLevel));
        CustomUserDetails principal = new CustomUserDetails(claims.getSubject(), "", authorities, accessLevel);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT signature.");
            System.out.println(String.format("Invalid JWT signature trace: %s", e));
        }

        return false;
    }
}
