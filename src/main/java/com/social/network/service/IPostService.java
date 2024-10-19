package com.social.network.service;

import java.util.List;
import java.util.Optional;

import com.social.network.domain.UserPost;

public interface IPostService
{

	public Optional<UserPost> addUserPost(UserPost profilePost);

	public List<UserPost> getAllUserPost(UserPost profilePost);

	public UserPost getUserPostById(UserPost profilePost);

	public Optional<UserPost> deleteUserPost(UserPost profilePost);
}
