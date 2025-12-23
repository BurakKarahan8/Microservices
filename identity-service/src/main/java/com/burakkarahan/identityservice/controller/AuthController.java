package com.burakkarahan.identityservice.controller;

import com.burakkarahan.identityservice.dto.AuthRequest;
import com.burakkarahan.identityservice.model.UserCredential;
import com.burakkarahan.identityservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String addNewUser(@RequestBody UserCredential user) {
        return service.saveUser(user);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (authenticate.isAuthenticated()) {
            return service.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Geçersiz kullanıcı erişimi!");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token geçerli";
    }
}