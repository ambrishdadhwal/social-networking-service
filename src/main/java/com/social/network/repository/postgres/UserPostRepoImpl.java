package com.social.network.repository.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.social.network.entity.postgres.UserPostE;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class UserPostRepoImpl
{

	@Autowired(required = false)
	EntityManager entityManager;

	public UserPostE getUserByIdWithNamedQuery(Long id)
	{
		Query namedQuery = entityManager.createNamedQuery("UserPostE.findByPostId");
		namedQuery.setParameter("postId", id);
		return (UserPostE)namedQuery.getSingleResult();
	}

}
