package com.springcatalog.application.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springcatalog.application.domain.Image;
import com.springcatalog.application.domain.Product;
import com.springcatalog.application.service.ProductService;
import com.springcatalog.application.service.resource.ImageResource;
import com.springcatalog.application.service.resource.ImageResourceAssembler;
import com.springcatalog.application.service.resource.LightProductResource;
import com.springcatalog.application.service.resource.LightProductResourceAssembler;
import com.springcatalog.application.service.resource.ProductResource;
import com.springcatalog.application.service.resource.ProductResourceAssembler;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;

	@Autowired
	private ProductResourceAssembler productAssembler;

	@Autowired
	private LightProductResourceAssembler lightProductAssembler;

	@Autowired
	private ImageResourceAssembler imageAssembler;

	

	@GetMapping("/catalog")
	public ResponseEntity<List<ProductResource>> getAllProductsWithInformation() throws JsonProcessingException {
		List<Product> listOfProducts = service.getAllProducts();
		List<ProductResource> productList = productAssembler.toResources(listOfProducts);
		return new ResponseEntity<List<ProductResource>>(productList, HttpStatus.OK);
	}

	@GetMapping("/products")
	public ResponseEntity<List<LightProductResource>> getAllProducts() throws JsonProcessingException {
		List<Product> listOfProducts = service.getAllProducts();
		List<LightProductResource> productList = lightProductAssembler.toResources(listOfProducts);
		return new ResponseEntity<List<LightProductResource>>(productList, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<LightProductResource> getProduct(@PathVariable Long productId) {
		Product product = service.findProduct(productId);
		LightProductResource productDto = lightProductAssembler.toResource(product);
		return new ResponseEntity<LightProductResource>(productDto, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/info")
	public ResponseEntity<ProductResource> getProductWithInfo(@PathVariable Long productId) {
		Product product = service.findProduct(productId);
		ProductResource productDto = productAssembler.toResource(product);
		return new ResponseEntity<ProductResource>(productDto, HttpStatus.OK);
	}

	@PostMapping("products")
	public ResponseEntity<Void> addProduct(@RequestBody ProductResource productResource) {
		Product product = convertResourceToProduct(productResource);
		service.addProduct(product);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PutMapping("products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody ProductResource productDto) {
		Product product = convertResourceToProduct(productDto);
		if (productId != null) {
			product.setProductId(productId);
		}
		service.updateProduct(product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@DeleteMapping("products/{productId}")
	public ResponseEntity<Void> removeProduct(@PathVariable Long productId) {
		service.removeProduct(productId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	@GetMapping("/products/{productId}/images")
	public ResponseEntity<List<ImageResource>> getAllImages(@PathVariable Long productId) {
		List<Image> imageOfImages = service.getAllImages(productId);
		List<ImageResource> imageList = imageAssembler.toResources(imageOfImages);
		return new ResponseEntity<List<ImageResource>>(imageList, HttpStatus.OK);
	}

	@GetMapping("/products/{productId}/images/{imageId}")
	public ResponseEntity<ImageResource> getImage(@PathVariable Long imageId) {
		Image image = service.findImage(imageId);
		ImageResource resource = imageAssembler.toResource(image);
		return new ResponseEntity<ImageResource>(resource, HttpStatus.OK);
	}

	@PostMapping("/products/{productId}/images")
	public ResponseEntity<Void> addImage(@PathVariable Long productId, @RequestBody ImageResource imageDto) {
		Product product = service.findProduct(productId);
		Image image = convertResourceToImage(imageDto);
		if (product != null) {
			image.setProduct(product);
			service.addImage(image);
		}
		return new ResponseEntity<Void>(HttpStatus.CREATED);
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
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Exception> handleBadRequests(Exception exception){
		return new ResponseEntity<Exception>(exception, HttpStatus.CONFLICT);
	}

	private Product convertResourceToProduct(ProductResource productDto) {
		ModelMapper modelMapper = new ModelMapper();
		Product product = modelMapper.map(productDto, Product.class);
		Product productParent = null;
		if(productDto.getParentProduct() != null) {
			productParent = service.findProduct(productDto.getParentProduct().getProductId());
			product.setParentProduct(productParent);
		}
		return product;
	}

	private Image convertResourceToImage(ImageResource imageDto) {
		ModelMapper modelMapper = new ModelMapper();
		Image image = modelMapper.map(imageDto, Image.class);
		return image;
	}

}