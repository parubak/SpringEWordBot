package com.mygroup.springewordbot.db.repo;

import com.mygroup.springewordbot.db.model.User;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> getAllBySendingTrue();


//    @Transactional
//    @Query("update User set sending=false where id=:id")
//    void setSendingById(@Param("id") Long id);

//    @Modifying
//    @Query("UPDATE User u SET u.viewWord = :word WHERE u.id= :user")
//    void setViewWordById(@Param("user") Long userId, @Param("word") Long wordId);
}
