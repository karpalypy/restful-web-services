package com.karpalypy.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.karpalypy.rest.webservices.restfulwebservices.exception.UserNotFoundException;

@RestController
public class UserJPAResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/jpa/users")
	public List<UserEntity> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public Resource<UserEntity> retrieveUser(@PathVariable int id) {
		Optional<UserEntity> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UserNotFoundException("id-" + id);

		// "all-users", SERVER_PATH + "/users"
		// retrieveAllUsers
		Resource<UserEntity> resource = new Resource<UserEntity>(user.get());

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resource.add(linkTo.withRel("all-users"));

		// HATEOAS

		return resource;
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	//
	// input - details of user
	// output - CREATED & Return the created URI

	// HATEOAS

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity user) {
		UserEntity savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUserPost(@PathVariable int id) {
		Optional<UserEntity> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		return userOptional.get().getPosts();
	}


	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<UserEntity> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		UserEntity user = userOptional.get();
		
		post.setUser(user);
		
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

}