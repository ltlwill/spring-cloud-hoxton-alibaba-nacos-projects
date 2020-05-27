package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.efe.ms.crawlerservice.web.sys.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserController.class,webEnvironment = WebEnvironment.RANDOM_PORT)
//@SpringBootConfiguration
//@WebMvcTest(UserController.class)
public class EmailServiceApplicationTests {
	
	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
		System.out.println(mvc);
		
	}

}
