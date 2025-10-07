package com.pranesh.blog.controllers;

import com.pranesh.blog.entities.User;
import com.pranesh.blog.exceptions.ResourceNotFoundException;
import com.pranesh.blog.repositories.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.pranesh.blog.exceptions.ApiException;
import com.pranesh.blog.payloads.JwtAuthRequest;
import com.pranesh.blog.payloads.JwtAuthResponse;
import com.pranesh.blog.payloads.UserDto;
import com.pranesh.blog.security.JwtService;
import com.pranesh.blog.services.UserService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(request.getUsername());
        return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {
        UserDto newUser = userService.registerNewUser(userDto);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();

        UserDto userDto = userService.getUserByEmail(email);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public void handleBadCredentialsException() {
        throw new ApiException("Invalid Username or password");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleResourceNotFoundException() {
    }
}
