package com.social.network.repository;

import java.util.List;
import java.util.Optional;

import com.social.network.entity.UserProfileE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userRepo")
public interface UserRepo extends JpaRepository<UserProfileE, Long>
{

	@Query(value = "SELECT * FROM social_user WHERE id = ?1", nativeQuery = true)
	Optional<UserProfileE> findById(long userId);

	@Query(value = "SELECT p FROM UserProfileE p WHERE p.email =:email ")
	Optional<UserProfileE> findByEmail(@Param("email") String email);

	@Query(value = "SELECT * FROM social_user WHERE first_name ilike  %:fName%", nativeQuery = true)
	List<UserProfileE> findByFirstNameContaining(@Param("fName") String fName);
}
