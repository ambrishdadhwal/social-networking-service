package com.social.network.service;

import java.util.List;
import java.util.Optional;

import com.social.network.domain.UserPost;
import com.social.network.presentation.CommonResponse;

public interface IPostService
{

	public Optional<UserPost> addUserPost(UserPost profilePost);

	public List<UserPost> getAllUserPost(Long userId);

	public UserPost getUserPostByUserIdAndPostId(Long userId, Long postId);

    CommonResponse<Boolean> deleteAllUserPost(Long userId);

    public  CommonResponse<Boolean> deleteUserPost(Long userId, Long postId);
}
