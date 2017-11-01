package com.springcatalog.application.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product_catalog")
public class Product implements Serializable {

	private static final long serialVersionUID = 6802872110370480823L;

	@Id
	@Column(name = "id_product", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	@Column(name = "ds_name", nullable = false)
	private String name;

	@Column(name = "ds_description", nullable = false)
	private String description;

	@ManyToOne(cascade = { CascadeType.ALL }, optional = true)
	@JoinColumn(name = "parent_product_id")
	private Product parentProduct;

	@OneToMany(mappedBy = "parentProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Product> productChildren = new ArrayList<Product>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Image> images;

	public Long getProductId() {
		return productId;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Product getParentProduct() {
		return parentProduct;
	}

	public List<Product> getProductChildren() {
		return productChildren;
	}

	public List<Image> getImages() {
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

	public void setParentProduct(Product parentProduct) {
		this.parentProduct = parentProduct;
	}

	public void setProductChildren(List<Product> productChildren) {
		this.productChildren = productChildren;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}
