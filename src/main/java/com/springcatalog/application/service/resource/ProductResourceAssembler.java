package com.springcatalog.application.service.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.springcatalog.application.controller.ProductController;
import com.springcatalog.application.domain.Image;
import com.springcatalog.application.domain.Product;

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

		List<Image> images = product.getImages();
		List<Product> products = product.getProductChildren();

		for (Image image : images) {
			productResource.add(linkTo(ProductController.class)
					.slash("products")
					.slash(product.getProductId())
					.slash("images")
					.slash(image.getId())
					.withRel("image"));
		}

		for (Product productChild : products) {
			productResource.add(linkTo(ProductController.class)
					.slash("products")
					.slash(product.getProductId())
					.slash("subproducts")
					.slash(productChild.getProductId())
					.withRel("subproduct"));
		}
		productResource.add(linkTo(ProductController.class)
				.slash("products")
				.slash(product.getProductId())
				.withSelfRel());
		return productResource;
	}

	@Override
	public List<ProductResource> toResources(Iterable<? extends Product> products) {
		List<ProductResource> resources = new ArrayList<ProductResource>();
		for (Product product : products) {
			resources.add(toResource(product));
		}
		return resources;
	}
}
