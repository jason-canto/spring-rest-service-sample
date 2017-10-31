package com.avenuecode.application.service.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

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

		productResource.add(linkTo(ProductController.class)
				.slash("products")
				.slash(product.getProductId())
				.withSelfRel());

		return productResource;
	}

	@Override
	public List<LightProductResource> toResources(Iterable<? extends Product> products) {
		List<LightProductResource> resources = new ArrayList<LightProductResource>();
		for (Product product : products) {
			resources.add(toResource(product));
		}
		return resources;
	}
}
