package com.avenuecode.demo.restdemo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.avenuecode.application.RestdemoApplication;
import com.avenuecode.application.controller.ProductController;
import com.avenuecode.application.domain.Product;
import com.avenuecode.application.service.resource.ProductResource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={RestdemoApplication.class})
public class RestdemoApplicationTests {

	@Autowired
	private ProductController masterController;

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	public void whenConvertEntityToProduct() {
		Product product = new Product();
		product.setProductId(Long.valueOf(1));
		product.setDescription("Test Description");
		product.setDescription("Test name");

		ProductResource productResource = modelMapper.map(product, ProductResource.class);
		assertEquals(product.getProductId(), productResource.getProductId());
		assertEquals(product.getDescription(), productResource.getDescription());
		assertEquals(product.getName(), productResource.getName());
	}

}
