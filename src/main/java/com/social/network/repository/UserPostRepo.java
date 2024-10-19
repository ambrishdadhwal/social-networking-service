package com.social.network.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.social.network.entity.UserPostE;

public interface UserPostRepo extends CrudRepository<UserPostE, Long>
{

	@Query("select u from UserPostE u where u.user.id = ?1")
	List<UserPostE> getPostsByProfile(Long userId);

}
