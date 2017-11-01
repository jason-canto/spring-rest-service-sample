package com.avenuecode.demo.restdemo;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.modelmapper.ModelMapper;

import org.springframework.boot.test.context.SpringBootTest;

import com.avenuecode.application.RestdemoApplication;
import com.avenuecode.application.controller.ProductController;
import com.avenuecode.application.domain.Product;
import com.avenuecode.application.service.resource.ImageResource;
import com.avenuecode.application.service.resource.ProductResource;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.mockito.InjectMocks;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { RestdemoApplication.class })
public class RestdemoApplicationTests {

	private ModelMapper modelMapper = new ModelMapper();

	private static final String RESOURCE_LOCATION = "http://localhost:8085";

	@InjectMocks
	ProductController controller;

	@Autowired
	WebApplicationContext context;

	@Autowired
	Gson json;

	private MockMvc mvc;

	@Before
	public void initTests() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void aShouldHaveEmptyDB() throws Exception {
		mvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void bWhenConvertEntityToProduct() {
		Product product = new Product();
		product.setProductId(Long.valueOf(1));
		product.setDescription("Test Description");
		product.setName("Test name");

		ProductResource productResource = modelMapper.map(product, ProductResource.class);
		assertEquals(product.getProductId(), productResource.getProductId());
		assertEquals(product.getDescription(), productResource.getDescription());
		assertEquals(product.getName(), productResource.getName());
	}

	@Test
	public void cShouldCreateFindProduct() throws Exception {
		String jsonProduct = json.toJson(getProductResource());
		mvc.perform(post(RESOURCE_LOCATION + "/products")
				.content(jsonProduct.getBytes())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();
		
		ProductResource product = getProductResource();
		mvc.perform(get(RESOURCE_LOCATION +"/products/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(product.getName())))
				.andExpect(jsonPath("$.description", is(product.getDescription())));
	}

	@Test
	public void dShouldCreateFindDeleteImage() throws Exception {

		ImageResource image = new ImageResource();
		image.setType("jpg");

		String jsonImage = json.toJson(image);
		mvc.perform(post(RESOURCE_LOCATION + "/products/1/images")
				.content(jsonImage.getBytes())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		mvc.perform(get(RESOURCE_LOCATION +"/products/1/images/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is(image.getType())));
	}

	@Test
	public void eShouldUpdateFindDeleteImage() throws Exception {

		ImageResource image = new ImageResource();
		image.setType("png");
		String jsonImage = json.toJson(image);

		mvc.perform(put(RESOURCE_LOCATION + "/products/1/images/1")
				.content(jsonImage.getBytes())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		mvc.perform(get(RESOURCE_LOCATION +"/products/1/images/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.type", is(image.getType())));

		mvc.perform(delete(RESOURCE_LOCATION + "/products/1/images/1"))
			.andExpect(status().isAccepted());
	}

	@Test
	public void fShouldUpdateFindProduct() throws Exception {

		ProductResource product = getProductResource();
		product.setDescription("teste description 2");
		product.setName("teste 2");

		String jsonProduct = json.toJson(product);

		mvc.perform(put(RESOURCE_LOCATION + "/products/1")
				.content(jsonProduct.getBytes())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		mvc.perform(get(RESOURCE_LOCATION +"/products/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(product.getName())))
				.andExpect(jsonPath("$.description", is(product.getDescription())));
	}

	@Test
	public void gShouldDeleteFindProduct() throws Exception {
		mvc.perform(delete(RESOURCE_LOCATION + "/products/1"))
				.andExpect(status().isAccepted());
	
		mvc.perform(get("/products")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	private ProductResource getProductResource() {
		ProductResource product = new ProductResource();
		product.setDescription("teste description");
		product.setName("teste");
		product.setProductId(Long.valueOf(1));
		return product;
	}

}
