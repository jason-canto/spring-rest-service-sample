package com.avenuecode.application.service.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;

import com.avenuecode.application.controller.ProductController;
import com.avenuecode.application.domain.Image;

@Component
public class ImageResourceAssembler extends ResourceAssemblerSupport<Image, ImageResource> {

	public ImageResourceAssembler() {
		super(ProductController.class, ImageResource.class);
	}

	@Override
	public ImageResource toResource(Image image) {
		ModelMapper modelMapper = new ModelMapper();
		ImageResource resource = instantiateResource(image);
		resource = modelMapper.map(image, ImageResource.class);
		resource.add(linkTo(ProductController.class)
				.slash("products")
				.slash(image.getProduct().getId())
				.withRel("product"));
		resource.add(linkTo(ProductController.class)
				.slash("products")
				.slash(image.getProduct().getId())
				.slash("images")
				.slash(image.getId())
				.withSelfRel());
		return resource;
	}
}
