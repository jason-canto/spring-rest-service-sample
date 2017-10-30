package com.avenuecode.application.service.resource;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.avenuecode.application.controller.ProductController;
import com.avenuecode.application.domain.Image;

@Component
public class ImageResourceAssembler extends ResourceAssemblerSupport<Image, ImageResource> {

	public ImageResourceAssembler() {
		super(ProductController.class, ImageResource.class);
	}

	@Override
	public ImageResource toResource(Image image) {
		ImageResource imageResource = instantiateResource(image);
		ModelMapper modelMapper = new ModelMapper();
		imageResource = modelMapper.map(image, ImageResource.class);
		return imageResource;
	}
}
