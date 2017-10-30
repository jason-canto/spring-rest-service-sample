package com.avenuecode.application.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.avenuecode.application.domain.Image;

public interface ImageCrud extends CrudRepository<Image, Long>{

	public List<Image> findByProductId(Long id);

}
