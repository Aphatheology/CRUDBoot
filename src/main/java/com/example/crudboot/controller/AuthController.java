package com.example.crudboot.controller;

import com.example.crudboot.dto.UserDto;
import com.example.crudboot.entity.Users;
import com.example.crudboot.event.RegistrationCompleteEvent;
import com.example.crudboot.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ApplicationEventPublisher publisher;


    public AuthController(AuthService authService, ApplicationEventPublisher publisher) {
        this.authService = authService;
        this.publisher = publisher;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserDto registerBody, final HttpServletRequest request) {
        Users user = authService.register(registerBody);

        this.publisher.publishEvent(new RegistrationCompleteEvent(user, this.authService.getApplicationUrl(request)));

        return new ResponseEntity<>("Successful", HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(authService.verifyToken(token), HttpStatus.OK);
    }

    @GetMapping("/resend-verification")
    public ResponseEntity<String> resendVerificationToken(@RequestParam("token") String oldToken, final HttpServletRequest request) {
        return new ResponseEntity<>(authService.resendVerificationToken(oldToken, request), HttpStatus.OK);
    }




}
