package org.geektimes.projects.user.service;

import org.geektimes.projects.framework.jdbc.DBConnectionManager;
import org.geektimes.projects.user.entity.User;
import org.geektimes.projects.user.repository.IUserRepository;
import org.geektimes.projects.user.repository.impl.UserRepositoryImpl;

public class UserService {
	
	private IUserRepository userRepository = null;
	
	public UserService() {
		 userRepository = new UserRepositoryImpl(new DBConnectionManager());
	}

	public void saveUser(User user) {
		userRepository.saveUser(user);
	}
	

}
