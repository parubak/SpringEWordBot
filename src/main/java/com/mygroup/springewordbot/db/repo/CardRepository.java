package com.mygroup.springewordbot.db.repo;

import com.mygroup.springewordbot.db.model.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends CrudRepository<Card,Long > {


     Card findFirstByUser_IdAndWord_Id(Long user_id, Long word_id);
}
