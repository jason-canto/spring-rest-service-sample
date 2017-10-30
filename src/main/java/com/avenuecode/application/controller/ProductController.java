package com.avenuecode.application.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.avenuecode.application.service.dto.LightProductDTO;
import com.avenuecode.application.service.dto.ProductDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/catalog")
	public ResponseEntity<List<ProductDTO>> getAllProductsWithInformation() throws JsonProcessingException {
		List<Product> listOfProducts = service.getAllProducts();
		ModelMapper modelMapper = new ModelMapper();
		List<ProductDTO> productList = Arrays.asList(modelMapper.map(listOfProducts, ProductDTO[].class));
		return new ResponseEntity<List<ProductDTO>>(productList, HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<LightProductDTO>> getAllProducts() throws JsonProcessingException {
		List<Product> listOfProducts = service.getAllProducts();
		ModelMapper modelMapper = new ModelMapper();
		List<LightProductDTO> productList = Arrays.asList(modelMapper.map(listOfProducts, LightProductDTO[].class));
		return new ResponseEntity<List<LightProductDTO>>(productList, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<LightProductDTO> getProduct(@PathVariable Long productId) {
		Product product = service.findProduct(productId);
		ModelMapper modelMapper = new ModelMapper();
		LightProductDTO productList = modelMapper.map(product, LightProductDTO.class);
		return new ResponseEntity<LightProductDTO>(productList, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/info")
	public ResponseEntity<ProductDTO> getProductWithInfo(@PathVariable Long productId) {
		Product product = service.findProduct(productId);
		ModelMapper modelMapper = new ModelMapper();
		ProductDTO productList = modelMapper.map(product, ProductDTO.class);
		return new ResponseEntity<ProductDTO>(productList, HttpStatus.OK);
	}

	@PostMapping("products")
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		if (product.getParentProduct() != null) {
			product.getParentProduct();
		}
		service.addProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}

	@PutMapping("products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
		if (productId != null) {
			product.setId(productId);
		}
		service.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@DeleteMapping("products/{productId}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long productId) {
		service.removeProduct(productId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/images")
	public ResponseEntity<List<Image>> getAllImages(@PathVariable Long productId) {
		List<Image> list = service.getAllImages(productId);
		return new ResponseEntity<List<Image>>(list, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/images/{imageId}")
	public ResponseEntity<Image> getImage(@PathVariable Long imageId) {
		Image image = service.findImage(imageId);
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@PostMapping("/products/{productId}/images")
	public ResponseEntity<Image> addImage(@PathVariable Long productId, @RequestBody Image image) {
		Product product = service.findProduct(productId);
		if (product != null) {
			image.setProduct(product);
			service.addImage(image);
		}
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@PutMapping("/products/{productId}/images/{imageId}")
	public ResponseEntity<Image> updateImage(@PathVariable Long productId, @PathVariable Long imageId,
			@RequestBody Image image) {
		Product product = service.findProduct(productId);
		if (product != null) {
			image.setProduct(product);
		}

		if (imageId != null) {
			image.setId(imageId);
		}
		service.updateImage(image);
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@DeleteMapping("products/{productId}/images/{imageId}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
		service.deleteImage(imageId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}