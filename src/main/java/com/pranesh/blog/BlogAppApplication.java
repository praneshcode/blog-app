package com.pranesh.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pranesh.blog.config.AppConstants;
import com.pranesh.blog.entities.Role;
import com.pranesh.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner {

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");

        // check if roles already exist in DB
        List<Role> existingRoles = roleRepo.findAll();

        if (existingRoles.isEmpty()) {
            roleRepo.saveAll(List.of(roleUser, roleAdmin));
            System.out.println("✅ Default roles created");
        } else {
            System.out.println("⚠️ Roles already exist");
        }
    }

}
