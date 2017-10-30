package com.avenuecode.application.service.resource;

import org.springframework.hateoas.ResourceSupport;

public class ImageResource extends ResourceSupport {

	private Long imageId;
	private String type;
	private Long productId;

	public Long getImageId() {
		return imageId;
	}

	public String getType() {
		return type;
	}

	public Long getProductId() {
		return productId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

}
