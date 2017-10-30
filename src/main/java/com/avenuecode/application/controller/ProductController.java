package com.avenuecode.application.controller;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
import com.avenuecode.application.service.resource.ImageResource;
import com.avenuecode.application.service.resource.ImageResourceAssembler;
import com.avenuecode.application.service.resource.LightProductResource;
import com.avenuecode.application.service.resource.ProductResource;
import com.avenuecode.application.service.resource.ProductResourceAssembler;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductResourceAssembler resourceAssembler;

	@Autowired
	private ImageResourceAssembler imageAssembler;

	@GetMapping("/catalog")
	public ResponseEntity<List<ProductResource>> getAllProductsWithInformation() throws JsonProcessingException {
		List<Product> listOfProducts = service.getAllProducts();
		ModelMapper modelMapper = new ModelMapper();
		List<ProductResource> productList = Arrays.asList(modelMapper.map(listOfProducts, ProductResource[].class));
		return new ResponseEntity<List<ProductResource>>(productList, HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<LightProductResource>> getAllProducts() throws JsonProcessingException {
		List<Product> listOfProducts = service.getAllProducts();
		ModelMapper modelMapper = new ModelMapper();
		List<LightProductResource> productList = Arrays
				.asList(modelMapper.map(listOfProducts, LightProductResource[].class));
		return new ResponseEntity<List<LightProductResource>>(productList, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<LightProductResource> getProduct(@PathVariable Long productId) {
		Product product = service.findProduct(productId);
		LightProductResource productDto = convertLightProductToResource(product);
		return new ResponseEntity<LightProductResource>(productDto, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/info")
	public ResponseEntity<ProductResource> getProductWithInfo(@PathVariable Long productId) {
		Product product = service.findProduct(productId);
		ProductResource productDto = resourceAssembler.toResource(product);
		return new ResponseEntity<ProductResource>(productDto, HttpStatus.OK);
	}

	@PostMapping("products")
	public ResponseEntity<Product> addProduct(@RequestBody ProductResource productDto) {
		Product product = convertResourceToProduct(productDto);
		service.addProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.CREATED);
	}

	@PutMapping("products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody ProductResource productDto) {
		Product product = convertResourceToProduct(productDto);
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
	public ResponseEntity<List<ImageResource>> getAllImages(@PathVariable Long productId) {
		ModelMapper modelMapper = new ModelMapper();
		List<Image> imageOfImages = service.getAllImages(productId);
		List<ImageResource> imageList = Arrays.asList(modelMapper.map(imageOfImages, ImageResource[].class));
		return new ResponseEntity<List<ImageResource>>(imageList, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/images/{imageId}")
	public ResponseEntity<ImageResource> getImage(@PathVariable Long imageId) {
		Image image = service.findImage(imageId);
		ImageResource imageDto = imageAssembler.toResource(image);
		return new ResponseEntity<ImageResource>(imageDto, HttpStatus.OK);
	}

	@PostMapping("/products/{productId}/images")
	public ResponseEntity<Image> addImage(@PathVariable Long productId, @RequestBody ImageResource imageDto) {
		Product product = service.findProduct(productId);
		Image image = convertResourceToImage(imageDto);
		if (product != null) {
			image.setProduct(product);
			service.addImage(image);
		}
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@PutMapping("/products/{productId}/images/{imageId}")
	public ResponseEntity<Image> updateImage(@PathVariable Long productId, @PathVariable Long imageId,
			@RequestBody ImageResource imageDto) {
		Product product = service.findProduct(productId);
		Image image = convertResourceToImage(imageDto);
		if (product != null) {
			image.setProduct(product);
		}

		if (imageId != null) {
			image.setImageId(imageId);
		}
		service.updateImage(image);
		return new ResponseEntity<Image>(image, HttpStatus.OK);
	}

	@DeleteMapping("products/{productId}/images/{imageId}")
	public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
		service.deleteImage(imageId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	private Image convertResourceToImage(ImageResource imageDto) {
		ModelMapper modelMapper = new ModelMapper();
		Image image = modelMapper.map(imageDto, Image.class);
		return image;
	}

	private ImageResource convertImageToResource(Image image) {
		ModelMapper modelMapper = new ModelMapper();
		ImageResource imageDto = modelMapper.map(image, ImageResource.class);
		return imageDto;
	}

	private Product convertResourceToProduct(ProductResource productDto) {
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(productDto, Product.class);
		return product;
	}

	private ProductResource convertProductToResource(Product product) {
		ModelMapper modelMapper = new ModelMapper();
		ProductResource productDto = modelMapper.map(product, ProductResource.class);
		return productDto;
	}

	private LightProductResource convertLightProductToResource(Product product) {
		ModelMapper modelMapper = new ModelMapper();
		LightProductResource productDto = modelMapper.map(product, LightProductResource.class);
		return productDto;
	}
}