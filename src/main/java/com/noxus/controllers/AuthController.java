package com.noxus.controllers;

import com.noxus.data.dto.security.AccountCredentialsDTO;
import com.noxus.services.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService service;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody AccountCredentialsDTO credentials) {
        if (credentialsIsInvalid(credentials))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = service.signIn(credentials);

        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return ResponseEntity.ok().body(token);
    }

    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refreshToken(
        @PathVariable("username") String username,
        @RequestHeader("Authorization") String refreshToken) {
        if (parametersAreInvalid(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = service.refreshToken(username, refreshToken);
        if (token == null) ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return  ResponseEntity.ok().body(token);
    }

    private boolean parametersAreInvalid(String username, String refreshToken) {
        return StringUtils.isBlank(username) || StringUtils.isBlank(refreshToken);
    }

    private static boolean credentialsIsInvalid(AccountCredentialsDTO credentials) {
        return credentials == null ||
            StringUtils.isBlank(credentials.getPassword()) ||
            StringUtils.isBlank(credentials.getUsername());
    }
}
