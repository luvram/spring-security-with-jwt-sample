package com.spring.security.jwt;


import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@AllArgsConstructor
@RestController
public class SimpleController {
    private TokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/sign-up")
    public void signUp(@Valid @RequestBody SignUpDto signUpDto) {
        userDetailsService.createUser(signUpDto.getLoginId(), signUpDto.getPassword(), signUpDto.getAccessLevel());
    }

    @PostMapping("/sign-in")
    public JwtToken signIn(@Valid @RequestBody SignInDto signInDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
//        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        return new JwtToken(jwt);
    }

    @GetMapping("/user")
    public String getUser() {
        return "I'm User";
    }

    @GetMapping("/manager")
    public String getManager() {
        return "I'm Manager";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "I'm admin";
    }
}
