package com.social.network.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.network.entity.postgres.UserFriendE;

@Repository
public interface UserFriendRepo extends JpaRepository<UserFriendE, Long>
{

}
