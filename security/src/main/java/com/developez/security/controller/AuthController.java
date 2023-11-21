package com.developez.security.controller;

import com.developez.security.DTO.JwtAuthResponse;
import com.developez.security.DTO.LoginDto;
import com.developez.security.DTO.SignupDto;
import com.developez.security.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "CRUD REST APIs per Auth"
)
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController( AuthService authService ) {
        this.authService = authService;
    }

    // Costruzione della login REST API
    @Operation(
            summary = "Login",
            description = "Login",
            tags = { "CRUD REST APIs per Auth" }
    )
    @ApiResponse(
            responseCode = "200",
            description = "Login effettuato con successo"
    )
    @PostMapping(value = {"/login", "/signing"})
    public ResponseEntity<JwtAuthResponse> login( @RequestBody LoginDto loginDto ) {
        String token = authService.Login( loginDto );

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken( token );

        return new ResponseEntity<>( jwtAuthResponse, HttpStatus.OK );
    }

    // Costruzione della signup REST API
    @Operation(
            summary = "Registrazione",
            description = "Registrazione",
            tags = { "CRUD REST APIs per Auth" }
    )
    @ApiResponse(
            responseCode = "201",
            description = "Registrazione effettuata con successo"
    )
    @PostMapping(value = {"/register", "/signup"} )
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto ) {
        String response = authService.signup( signupDto );

        return new ResponseEntity<>( response, HttpStatus.CREATED );
    }

}
