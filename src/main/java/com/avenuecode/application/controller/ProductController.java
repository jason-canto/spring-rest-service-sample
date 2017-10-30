package com.avenuecode.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.avenuecode.application.domain.Image;
import com.avenuecode.application.domain.Product;
import com.avenuecode.application.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;


	@GetMapping("/products")
	public MappingJacksonValue getAllProducts() throws JsonProcessingException {
		List<Product> list = service.getAllProducts();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		mappingJacksonValue.setFilters(new SimpleFilterProvider().addFilter("product", SimpleBeanPropertyFilter.filterOutAllExcept("image")));
		//FilterProvider filter = new SimpleFilterProvider().addFilter("somefilter",SimpleBeanPropertyFilter.filterOutAllExcept("image"));
		//mappingJacksonValue.setFilters(filter);
		return mappingJacksonValue;
	}

	@GetMapping("/product/{productId}/info")
	public List<Product> getAllProductInfo() {
		List<Product> list = service.getAllProducts();
		return list;
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
		Product product = service.findProduct(productId);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PostMapping("product")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		if (product.getParentProduct() != null) {
			product.getParentProduct();
		}
		service.addProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}

	@PutMapping("product")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
		service.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@DeleteMapping("product/{productId}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long productId) {
		service.removeProduct(productId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/product/{productId}/images")
	public ResponseEntity<List<Image>> getAllImages(@PathVariable Long productId) {
		List<Image> list = service.getAllImages(productId);
		return new ResponseEntity<List<Image>>(list, HttpStatus.OK);
	}

	@GetMapping("/product/{productId}/image/{imageId}")
	public ResponseEntity<Image> getImage(@PathVariable Long imageId) {
		Image image = service.findImage(imageId);
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@PostMapping("/product/{productId}/image")
	public ResponseEntity<Image> addImage(@PathVariable Long productId, @RequestBody Image image) {
		Product product = service.findProduct(productId);
		if (product != null) {
			image.setProduct(product);
			service.addImage(image);
		}
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@PutMapping("/product/{productId}/image")
	public ResponseEntity<Image> updateImage(@PathVariable Long productId, @RequestBody Image image) {
		Product product = service.findProduct(productId);
		if (product != null) {
			image.setProduct(product);
		}
		service.updateImage(image);
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@DeleteMapping("product/{productId}/image/{imageId}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
		service.deleteImage(imageId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}