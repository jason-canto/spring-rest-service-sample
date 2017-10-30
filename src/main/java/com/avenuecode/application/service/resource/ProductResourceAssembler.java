package com.avenuecode.application.service.resource;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.avenuecode.application.controller.ProductController;
import com.avenuecode.application.domain.Product;

@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, ProductResource> {

	public ProductResourceAssembler() {
		super(ProductController.class, ProductResource.class);
	}

	@Override
	public ProductResource toResource(Product product) {
		ProductResource productResource = instantiateResource(product);
		ModelMapper modelMapper = new ModelMapper();
		productResource = modelMapper.map(product, ProductResource.class);
		productResource.add(ControllerLinkBuilder.linkTo(ProductController.class).slash(product.getId()).withSelfRel());
		return productResource;
	}
}
