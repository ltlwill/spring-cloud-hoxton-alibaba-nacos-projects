package com.efe.ms.productservice;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.efe.ms.productservice.web.ProductController;

/**
 * 
 * <p>
 * Mock测试应用:
 * </p>
 * 
 * @author Liu TianLong 2018年10月24日 上午9:57:03
 */

@RunWith(SpringJUnit4ClassRunner.class)
// @RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class ProductAppliactionTest {

	private MockMvc mockMvc;

	@Autowired
	private ProductController productController;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	public void productTest() throws Exception {
		Object result = null;
		// 1.测试分页获取产品信息
		result = mockMvc
				.perform(MockMvcRequestBuilders.get(URI.create("/products")))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);

		// 2.测试按SKU获取产品信息
		result = mockMvc
				.perform(
						MockMvcRequestBuilders.get(URI
								.create("/products/10067096")))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);

		// 3.测试按SKU获取产品组合信息
		result = mockMvc
				.perform(
						MockMvcRequestBuilders.get(URI
								.create("/products/combo/10067096")))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);

		// 4.测试按SKU获取产品组合中所有SKU信息
		result = mockMvc
				.perform(
						MockMvcRequestBuilders.get(URI
								.create("/products/combo/skus/10067096")))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);

		// 5.测试按SKU获取产品的主SKU
		result = mockMvc
				.perform(
						MockMvcRequestBuilders.get(URI
								.create("/products/combo/main/10067096")))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
				.getResponse().getContentAsString();
		System.out.println(result);
	}
}
