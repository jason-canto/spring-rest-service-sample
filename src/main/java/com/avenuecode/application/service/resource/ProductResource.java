package com.avenuecode.application.service.resource;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class ProductResource extends ResourceSupport {

	private Link id;
	private String name;
	private String description;
	private ProductResource parentProduct;
	private List<ProductResource> productChildren;
	private List<ImageResource> images;

	public ProductResource() {}

	public ProductResource(Link id, String name, String description, ProductResource parentProduct, List<ProductResource> productChildren, List<ImageResource> images) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.parentProduct = parentProduct;
		this.productChildren = productChildren;
		this.images = images;
	}

	public Link getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ProductResource getParentProduct() {
		return parentProduct;
	}

	public List<ProductResource> getProductChildren() {
		return productChildren;
	}

	public List<ImageResource> getImages() {
		return images;
	}

	public void setId(Link id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setParentProduct(ProductResource parentProduct) {
		this.parentProduct = parentProduct;
	}

	public void setProductChildren(List<ProductResource> productChildren) {
		this.productChildren = productChildren;
	}

	public void setImages(List<ImageResource> images) {
		this.images = images;
	}

}
