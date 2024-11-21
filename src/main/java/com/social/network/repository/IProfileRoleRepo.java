package com.social.network.repository;

import com.social.network.entity.UserProfileRoleE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfileRoleRepo extends JpaRepository<UserProfileRoleE, Long>
{

}
