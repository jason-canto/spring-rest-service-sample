package com.avenuecode.application.service.resource;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.avenuecode.application.controller.ProductController;
import com.avenuecode.application.domain.Product;

@Component
public class LightProductResourceAssembler extends ResourceAssemblerSupport<Product, LightProductResource> {

	public LightProductResourceAssembler() {
		super(ProductController.class, LightProductResource.class);
	}

	@Override
	public LightProductResource toResource(Product product) {
		LightProductResource productResource = instantiateResource(product);
		ModelMapper modelMapper = new ModelMapper();
		productResource = modelMapper.map(product, LightProductResource.class);
		return productResource;
	}
}
