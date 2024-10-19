package com.social.network.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social.network.entity.ProfileE;

@Repository("userRepo")
public interface UserRepo extends JpaRepository<ProfileE, Long>
{

	@Query(value = "SELECT * FROM social_user WHERE id = ?1", nativeQuery = true)
	Optional<ProfileE> findById(long userId);

	@Query(value = "SELECT p FROM ProfileE p WHERE p.email =:email ")
	Optional<ProfileE> findByEmail(@Param("email") String email);

	@Query(value = "SELECT * FROM social_user WHERE first_name ilike  %:fName%", nativeQuery = true)
	List<ProfileE> findByFirstNameContaining(@Param("fName") String fName);
}
