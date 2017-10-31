package com.avenuecode.application.service.resource;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class ProductResource extends ResourceSupport {

	private Long productId;
	private String name;
	private String description;

	@JsonBackReference
	private ProductResource parentProduct;
	private List<ProductResource> productChildren;
	private List<ImageResource> images;

	public ProductResource() {}

	public ProductResource(Long productId, String name, String description, ProductResource parentProduct, List<ProductResource> productChildren, List<ImageResource> images) {
		this.productId = productId;
		this.name = name;
		this.description = description;
		this.parentProduct = parentProduct;
		this.productChildren = productChildren;
		this.images = images;
	}

	public Long getProductId() {
		return productId;
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

	public void setProductId(Long productId) {
		this.productId = productId;
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
