package com.karpalypy.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {

	private static List<User> users = new ArrayList<>();

	private static int usersCount = 3;

	static {
		users.add(new User(1, "Karly", LocalDate.of(1988, Month.MARCH, 25)));
		users.add(new User(2, "Manuel", LocalDate.of(1989, Month.JULY, 28)));
		users.add(new User(3, "Chila", LocalDate.of(1955, Month.FEBRUARY, 28)));
	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}

	public User findOne(Integer id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public User deleteById(Integer id) {
		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			User _user = iterator.next();
			if (_user.getId() == id) {
				iterator.remove();
				return _user;
			}
		}
		return null;
	}
}
