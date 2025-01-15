package com.social.network.repository.postgres;

import com.social.network.entity.postgres.UserProfileRoleE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProfileRoleRepo extends JpaRepository<UserProfileRoleE, Long>
{

}
