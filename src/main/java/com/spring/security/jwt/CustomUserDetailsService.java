package com.spring.security.jwt;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        Optional<User> userOptional = userRepository.findByLoginId(loginId);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            return new CustomUserDetails(
                    user.getLoginId(),
                    user.getPassword(),
                    Collections.emptyList(),
                    user.getAccessLevel()
            );
        }
        throw new UsernameNotFoundException("User not found.");
    }

    @Transactional
    public void createUser(String loginId, String password, String accessLevel) {
        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(passwordEncoder.encode(password));
        user.setAccessLevel(accessLevel);
        user.setName(loginId);

        userRepository.save(user);
    }
}
