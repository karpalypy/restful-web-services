package com.karpalypy.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDAOService {

	private static List<UserEntity> users = new ArrayList<>();

	private static int usersCount = 3;

	static {
		users.add(new UserEntity(1, "Karly", LocalDate.of(1988, Month.MARCH, 25)));
		users.add(new UserEntity(2, "Manuel", LocalDate.of(1989, Month.JULY, 28)));
		users.add(new UserEntity(3, "Chila", LocalDate.of(1955, Month.FEBRUARY, 28)));
	}

	public List<UserEntity> findAll() {
		return users;
	}

	public UserEntity save(UserEntity user) {
		if (user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}

	public UserEntity findOne(Integer id) {
		for (UserEntity user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}
	
	public UserEntity deleteById(Integer id) {
		Iterator<UserEntity> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			UserEntity _user = iterator.next();
			if (_user.getId() == id) {
				iterator.remove();
				return _user;
			}
		}
		return null;
	}
}
