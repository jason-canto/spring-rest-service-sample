package com.springcatalog.application.service;

import org.springframework.data.repository.CrudRepository;

import com.springcatalog.application.domain.Product;

public interface ProductCrud extends CrudRepository<Product, Long>{

}
