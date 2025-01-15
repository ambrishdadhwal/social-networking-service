package com.social.network.repository.postgres;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.social.network.entity.postgres.UserPostE;

@Repository("dashboardRepo")
public interface IDashboardRepo extends CrudRepository<UserPostE, Long>
{


}
