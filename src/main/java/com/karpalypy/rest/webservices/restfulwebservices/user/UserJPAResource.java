package com.karpalypy.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
@RequestMapping(path = "/jpa")
public class UserJPAResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/users")
	public List<UserEntity> retrieveAllUsers() {
				
		return userRepository.findAll() ;
	}

	@GetMapping(path = "/users/{id}")
	public Resource<UserEntity> retrieveUser(@PathVariable Integer id) {
		
		Optional<UserEntity> _user = userRepository.findById(id);
		
		if(!_user.isPresent())
			throw new UserNotFoundException("id = " + id);
		
		Resource<UserEntity> resource = new Resource<UserEntity>(_user.get());
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity user) {
		UserEntity _user = userRepository.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(_user.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userRepository.deleteById(id);
		
	}
	
	//------------------------------------------------------------------------------------------------------------

	
	@GetMapping("/users/{id}/posts")
	public List<Post> retrieveAllPostsByUser(@PathVariable Integer id) {
		
		Optional<UserEntity> _user = userRepository.findById(id);
		if (!_user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}				
		return _user.get().getPosts();
	}

	@PostMapping("/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable Integer id, @Valid @RequestBody Post post) {
		
		Optional<UserEntity> _user = userRepository.findById(id);
		
		if (!_user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		UserEntity user = _user.get();
		
		post.setUser(user);
		
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId())
				.toUri();	

		return ResponseEntity.created(location).build();
	}
	
}
