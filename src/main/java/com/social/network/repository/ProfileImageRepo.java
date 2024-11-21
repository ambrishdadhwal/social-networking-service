package com.social.network.repository;

import com.social.network.entity.UserProfileImageE;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userImageRepo")
@Transactional
public interface ProfileImageRepo extends CrudRepository<UserProfileImageE, Long> {

    @Query("select u from UserProfileImageE u where u.user.id = ?1")
    List<UserProfileImageE> findByUserId(Long userId);

    @Query("select u from UserProfileImageE u where u.id = ?1 AND u.user.id = ?2")
    UserProfileImageE findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Query("delete from UserProfileImageE b where b.user.id = ?1 AND b.post.id = ?2")
    void deleteAllByUserIdAndPostId(Long userId, Long postId);

    @Modifying
    @Query("delete from UserProfileImageE b where b.user.id = ?1")
    void deleteAllByUserId(Long userId);


}
