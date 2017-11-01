package com.springcatalog.application.service.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class LightProductResource extends ResourceSupport {

	private Link id;
	private String name;
	private String description;

	public LightProductResource() {}

	public LightProductResource(Link id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
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

	public void setId(Link id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
