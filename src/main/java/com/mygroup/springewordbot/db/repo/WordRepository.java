package com.mygroup.springewordbot.db.repo;

import com.mygroup.springewordbot.db.model.Word;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {



    Word findTopByOrderById();

    //    @Query("SELECT w FROM Card c INNER JOIN Word w on c.word.id!=w.id and c.studied!=true")
//     List<Word> top3WordsByUserId(@Param(value = "userId") Long userId);
    @Query(value = "SELECT   w FROM Word w WHERE NOT EXISTS (SELECT w FROM Card c where c.word.id = w.id and c.user.id=:userid and c.studied)")
    List<Word> top3WordsByUserId(@Param(value = "userid") Long userId);

    @Modifying
    @Transactional
    @Query("update Card set studied=false where user.id=:user")
    void reserById(@Param("user") Long userId);


}
