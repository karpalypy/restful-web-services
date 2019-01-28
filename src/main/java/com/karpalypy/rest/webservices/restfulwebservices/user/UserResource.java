package com.karpalypy.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karpalypy.rest.webservices.restfulwebservices.exception.UserNotFoundException;

@RestController
@RequestMapping(path = "/users")
public class UserResource {

	@Autowired
	private UserDAOService service;

	@GetMapping
	public List<UserEntity> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping(path = "/{id}")
	public Resource<UserEntity> retrieveUser(@PathVariable Integer id) {
		
		UserEntity _user = service.findOne(id);
		if(_user == null)
			throw new UserNotFoundException("id-" + id);
		
		//HATEOAS
		//retrieveAllUsers
		
		Resource<UserEntity> resource = new Resource<UserEntity>(_user);
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}

	@PostMapping
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity user) {
		UserEntity _user = service.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(_user.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Integer id) {
		UserEntity _user = service.deleteById(id);
		
		if(_user == null)
			throw new UserNotFoundException("id = " + id);
		
	}
	
}
