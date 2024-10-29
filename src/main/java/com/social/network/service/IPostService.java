package com.social.network.service;

import java.util.List;
import java.util.Optional;

import com.social.network.domain.UserPost;

public interface IPostService
{

	public Optional<UserPost> addUserPost(UserPost profilePost);

	public List<UserPost> getAllUserPost(Long userId);

	public UserPost getUserPostByUserIdAndPostId(Long userId, Long postId);

	public Optional<UserPost> deleteUserPost(UserPost profilePost);
}
