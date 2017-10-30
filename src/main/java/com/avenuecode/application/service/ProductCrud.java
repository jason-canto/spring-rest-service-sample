package com.avenuecode.application.service;

import org.springframework.data.repository.CrudRepository;

import com.avenuecode.application.domain.Product;

public interface ProductCrud extends CrudRepository<Product, Long>{

}
