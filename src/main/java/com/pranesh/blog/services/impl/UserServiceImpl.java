package com.pranesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.pranesh.blog.payloads.RoleDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pranesh.blog.exceptions.ResourceNotFoundException;
import com.pranesh.blog.config.AppConstants;
import com.pranesh.blog.entities.Role;
import com.pranesh.blog.entities.User;
import com.pranesh.blog.payloads.UserDto;
import com.pranesh.blog.repositories.RoleRepo;
import com.pranesh.blog.repositories.UserRepo;
import com.pranesh.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    // we have a bean in main springboot class
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    // since we are using UserDto so we need to convert it
    // we can also use Modern mapper library instead of these two conversion methods
    // convert UserDto to User
    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
		/*		User user = new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword()); */

        return user;
    }

    // Convert User to UserDto
    private UserDto UserToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
		/*UserDto userDto = new UserDto();
		userDto.setAbout(user.getAbout());
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());*/
        return userDto;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);
        User savedUser = userRepo.save(user);

        return this.UserToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());

        User updatedUser = userRepo.save(user);
        UserDto userDto1 = this.UserToDto(updatedUser);

        return userDto1;
    }

    @Override
    public UserDto changeRole(RoleDto roleDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        Role role = roleRepo.findByName(roleDto.getName());
        if (role == null) {
            throw new ResourceNotFoundException("Role", "Name", roleDto.getName());
        }

        user.getRoles().clear();
        user.getRoles().add(role);
        User updatedUser = userRepo.save(user);

        return UserToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        UserDto userDto = this.UserToDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> userList = userRepo.findAll();
//		List<UserDto> userDtoList = new ArrayList<>();
//		for(User u : userList) {
//			userDtoList.add(this.UserToDto(u));
//		}
        // or
        List<UserDto> userDtoList = userList.stream().map(user -> UserToDto(user)).collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepo.delete(user);

    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);

        // encoding the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        // getting role
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

        user.getRoles().add(role);

        User savedUser = this.userRepo.save(user);

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email", email));
        return UserToDto(user);
    }

}
