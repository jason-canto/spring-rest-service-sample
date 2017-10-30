package com.avenuecode.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avenuecode.application.domain.Image;
import com.avenuecode.application.domain.Product;

@Service
public class ProductService {

	@Autowired
	private ProductCrud productCrud;

	@Autowired
	private ImageCrud imageCrud;

	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<Product>();
		productCrud.findAll().forEach(productList::add);
		return productList;
	}

	public Product findProduct(Long id) {
		return productCrud.findOne(id);
	}

	public void addProduct(Product product) {
		productCrud.save(product);
	}

	public void updateProduct(Product product) {
		productCrud.save(product);
	}

	public void removeProduct(Long id) {
		productCrud.delete(id);
	}

	public List<Image> getAllImages(Long productId) {
		List<Image> imageList = new ArrayList<Image>();
		imageCrud.findByProductId(productId).forEach(imageList::add);
		return imageList;
	}

	public Image findImage(Long id) {
		return imageCrud.findOne(id);
	}

	public void addImage(Image image) {
		imageCrud.save(image);
	}

	public void updateImage(Image image) {
		imageCrud.save(image);
	}

	public void deleteImage(Long id) {
		imageCrud.delete(id);
	}

}
