package com.social.network.service;

import java.util.List;
import java.util.Optional;

import com.social.network.domain.UserProfile;

public interface IUserService
{

	// params can be configurable in application.properties
	public Optional<UserProfile> saveUser(UserProfile user) throws Exception;

	public List<UserProfile> allUsers();

	public long totalSocialUsers();

	public Optional<UserProfile> getUser(UserProfile user) throws Exception;

	public Optional<UserProfile> updateUser(UserProfile user);

	public Optional<UserProfile> getUserbyId(Long userId);

	public Optional<UserProfile> getUserbyUserNameAndId(String userName, Long userId);

	public Optional<UserProfile> deleteUserById(Long userId) throws Exception;

	public Optional<UserProfile> getUserbyUserName(String userName);

	public Optional<UserProfile> getUserbyEmail(String email);

	public List<UserProfile> getUserbyuserEmail(String email);

	public List<UserProfile> searchUsers(String search);

	public List<UserProfile> getUsersSearchbyName(String name);

	public List<UserProfile> allUsersPaging(Integer pageNumber, Integer pageSize);

}
