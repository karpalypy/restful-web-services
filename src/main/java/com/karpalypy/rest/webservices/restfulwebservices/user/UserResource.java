package com.karpalypy.rest.webservices.restfulwebservices.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users")
public class UserResource {

	@Autowired
	private UserDAOService service;

	@GetMapping
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping(path = "/{id}")
	public User retrieveUser(@PathVariable Integer id) {
		return service.findOne(id);
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		User _user = service.save(user); 
		return _user;
	}
	
}
