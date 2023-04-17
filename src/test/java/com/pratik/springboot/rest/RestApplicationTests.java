package com.pratik.springboot.rest;

import com.pratik.springboot.rest.entity.ProductEntity;
import com.pratik.springboot.rest.repository.ProductRepo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class RestApplicationTests {

	@Autowired
	private ProductRepo repository;

	@Value("${resttemplate.services.url}")
	private String baseURL;

	@Test
	void contextLoads() {
		System.out.println("Print1: " + repository.findById(1).get());
		System.out.println("Print2: " + repository.findById(2).get());

		RestTemplate template = new RestTemplate();
		ProductEntity entity = template.getForObject(baseURL + "/2", ProductEntity.class);
		System.out.println(entity);
	}

	@Test
	void testGetProduct() {
		System.out.println(baseURL);
		RestTemplate template = new RestTemplate();
		ProductEntity product = template.getForObject(baseURL + "/1", ProductEntity.class);
		assertNotNull(product);
		assertEquals(1, product.getId());

	}


	@Test
	void testCreateProduct() {
		ProductEntity entity = new ProductEntity();
		entity.setName("Fourth Product");
		entity.setDescription("444");
		entity.setPrice(4000);
		RestTemplate template = new RestTemplate();
		ProductEntity newProduct = template.postForObject(baseURL, entity, ProductEntity.class);
		System.out.println(newProduct);
	}

	@Test
	void testPut() {
		RestTemplate template = new RestTemplate();
		// template.exchange is a generic method where we can specified the http request type
		ProductEntity entity = template.getForObject(baseURL + "/4", ProductEntity.class);

		System.out.println(entity);
		entity.setPrice(15000);
		template.put(baseURL, entity);

		entity = template.getForObject(baseURL +"/4", ProductEntity.class);
		System.out.println(entity);
	}

	@BeforeEach
	void setup() {
		ProductEntity entity = new ProductEntity();
		//entity.setId(1);
		entity.setDescription("First Product");
		entity.setName("Product Name 1");
		entity.setPrice(100);

		//System.out.println("entity ; " + entity);
		repository.save(entity);
		entity.setId(2);
		entity.setDescription("Second Product");
		entity.setName("Product Name 2");
		entity.setPrice(200);
		repository.save(entity);
	}
}
