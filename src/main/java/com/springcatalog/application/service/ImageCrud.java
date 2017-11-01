package com.springcatalog.application.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springcatalog.application.domain.Image;

public interface ImageCrud extends CrudRepository<Image, Long>{

	public List<Image> findByProductProductId(Long id);

}
