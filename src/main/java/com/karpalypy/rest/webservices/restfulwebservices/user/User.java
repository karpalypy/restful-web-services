package com.karpalypy.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="All details about the user.")
public class User {

	private Integer id;
	
	@ApiModelProperty(notes="Name should have atleast 2 characteres")
	@Size(min=2, message="Name should have atleast 2 characteres")
	private String name;
	
	@Past
	@ApiModelProperty(notes="Birth date should be in the past")
	private LocalDate birthDate;
	
	protected User() {
		
	}

	public User(Integer id, String name, LocalDate birthDate) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

}
