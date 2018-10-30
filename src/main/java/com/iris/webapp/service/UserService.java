package com.iris.webapp.service;

import java.util.List;

import com.iris.webapp.dto.UserDto;

public interface UserService {
	UserDto getUserById(Integer userId);

	void saveUser(UserDto userDto);

	List<UserDto> getAllUsers();
}
