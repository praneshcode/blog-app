package com.pranesh.blog.controllers;

import java.util.List;

import jakarta.validation.Valid;

import com.pranesh.blog.entities.User;
import com.pranesh.blog.payloads.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pranesh.blog.payloads.ApiResponse;
import com.pranesh.blog.payloads.UserDto;
import com.pranesh.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer userId) {
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUserDto);
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<UserDto> changeRole(@Valid @RequestBody RoleDto roleDto, @PathVariable("userId") Integer userId) {
        UserDto updatedUserDto = userService.changeRole(roleDto, userId);
        return ResponseEntity.ok(updatedUserDto);
    }

    // Admin only
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) // instead of ApiResponse we can also use ? as sometime we do not know the type
    {
        userService.deleteUser(userId);
//		return new ResponseEntity(Map.of("message","user Deleted successfully"),HttpStatus.OK);
        return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId) {
        UserDto fetchedUser = userService.getUserById(userId);
        return new ResponseEntity<UserDto>(fetchedUser, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getALLUsers() {
        List<UserDto> users = userService.getAllUsers();
        return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
    }
}
