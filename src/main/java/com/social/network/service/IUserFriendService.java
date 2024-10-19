package com.social.network.service;

public interface IUserFriendService
{

	public boolean updateFriendRequest(Long userId, Long friendId, boolean decision) throws ProfileException;
}
