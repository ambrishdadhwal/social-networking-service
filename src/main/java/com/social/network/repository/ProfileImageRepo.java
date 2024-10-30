package com.social.network.repository;

import com.social.network.entity.ProfileImageE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userImageRepo")
public interface ProfileImageRepo extends CrudRepository<ProfileImageE, Long> {

    @Query("select u from ProfileImageE u where u.user.id = ?1")
    List<ProfileImageE> findByUserId(Long userId);

    @Query("select u from ProfileImageE u where u.id = ?1 AND u.user.id = ?2")
    ProfileImageE findByIdAndUserId(Long id, Long userId);

}
