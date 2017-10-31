package com.avenuecode.application.service.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

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
				.slash(image.getProduct().getProductId())
				.withRel("product"));
		resource.add(linkTo(ProductController.class)
				.slash("products")
				.slash(image.getProduct().getProductId())
				.slash("images")
				.slash(image.getId())
				.withSelfRel());
		return resource;
	}

	@Override
	public List<ImageResource> toResources(Iterable<? extends Image> images) {
		List<ImageResource> resources = new ArrayList<ImageResource>();
		for (Image image : images) {
			resources.add(toResource(image));
		}
		return resources;
	}

	public Image convertResourceToImage(ImageResource imageDto) {
		ModelMapper modelMapper = new ModelMapper();
		Image image = modelMapper.map(imageDto, Image.class);
		return image;
	}
}
