package com.pranesh.blog.services;

import java.util.List;

import com.pranesh.blog.payloads.RoleDto;
import com.pranesh.blog.payloads.UserDto;

public interface UserService {

    UserDto registerNewUser(UserDto user);

    UserDto createUser(UserDto user);

    UserDto updateUser(UserDto user, Integer userId);

    UserDto changeRole(RoleDto role, Integer userId);

    UserDto getUserById(Integer userId);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

}
