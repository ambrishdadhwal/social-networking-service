package com.social.network.repository;

import com.social.network.entity.ProfileImageE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userImageRepo")
public interface ProfileImageRepo extends CrudRepository<ProfileImageE, Long> {

}
