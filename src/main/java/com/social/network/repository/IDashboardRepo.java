package com.social.network.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.social.network.domain.UserPost;
import com.social.network.entity.UserPostE;

@Repository("dashboardRepo")
public interface IDashboardRepo extends CrudRepository<UserPostE, Long>
{


}
