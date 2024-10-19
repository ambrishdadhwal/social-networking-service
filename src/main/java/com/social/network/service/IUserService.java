package com.social.network.service;

import java.util.List;
import java.util.Optional;

import com.social.network.domain.Profile;

public interface IUserService
{

	// params can be configurable in application.properties
	public Optional<Profile> saveUser(Profile user) throws Exception;

	public List<Profile> allUsers();

	public long totalSocialUsers();

	public Optional<Profile> getUser(Profile user) throws Exception;

	public Optional<Profile> updateUser(Profile user);

	public Optional<Profile> getUserbyId(Long userId);

	public Optional<Profile> getUserbyUserNameAndId(String userName, Long userId);

	public Optional<Profile> deleteUserById(Long userId);

	public Optional<Profile> getUserbyUserName(String userName);

	public Optional<Profile> getUserbyEmail(String email);

	public List<Profile> getUserbyuserEmail(String email);

	public List<Profile> searchUsers(String search);

	public List<Profile> getUsersSearchbyName(String name);

	public List<Profile> allUsersPaging(Integer pageNumber, Integer pageSize);

}
