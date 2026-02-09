package com.ramyasingavarapu.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramyasingavarapu.dto.error.ValidationError;
import com.ramyasingavarapu.dto.request.UserLoginRequest;
import com.ramyasingavarapu.dto.request.UserRegistrationRequest;
import com.ramyasingavarapu.dto.response.UserRegistrationResponse;
import com.ramyasingavarapu.entity.User;
import com.ramyasingavarapu.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
        Optional<User> existingUser = userRepository.findByUsername(registrationRequest.getUsername());

        if (existingUser.isPresent())
        {
            ValidationError error = new ValidationError("username", "Username is already taken");

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
        }

        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
        User user = new User(registrationRequest.getUsername(), encodedPassword);

        userRepository.save(user);

        UserRegistrationResponse response = new UserRegistrationResponse("User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        return ResponseEntity.ok("Login successful");
    }
}
