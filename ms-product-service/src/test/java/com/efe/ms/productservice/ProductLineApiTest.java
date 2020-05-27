package com.efe.ms.productservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProductLineApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testProductLine() throws Exception {
		String result = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/productline"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);
	}
}
