package com.social.network.repository.postgres;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.social.network.entity.postgres.UserPostE;

@Transactional
public interface UserPostRepo extends CrudRepository<UserPostE, Long>
{

	@Query("select u from UserPostE u where u.user.id = ?1")
	List<UserPostE> getPostsByProfile(Long userId);

	@Query("select u from UserPostE u where u.user.id = ?1 AND u.id = ?2")
	UserPostE getPostsByUserIdAndPostId(Long userId, Long postId);

	@Modifying
	@Query("delete from UserPostE b where b.id = ?1 AND b.user.id =?1")
	void deleteByPostIdAndUserId(Long postId, Long userId);

	@Modifying
	@Query("delete from UserPostE b where b.user.id =?1")
	void deleteByUserId(Long userId);

}
