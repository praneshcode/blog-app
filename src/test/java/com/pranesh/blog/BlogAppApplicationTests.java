package com.pranesh.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pranesh.blog.services.UserService;

@SpringBootTest
class BlogAppApplicationTests {

	@Autowired
	private UserService userService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void serviceTest() {
		String className = userService.getClass().getName();
		String packageName = userService.getClass().getPackageName();
		
		System.out.println(className);
		System.out.println(packageName);
	}
	

}
