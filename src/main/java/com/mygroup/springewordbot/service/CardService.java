package com.mygroup.springewordbot.service;


import com.mygroup.springewordbot.db.model.Card;
import com.mygroup.springewordbot.db.repo.CardRepository;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public void learnedWord(Long user, Long word){
        Card save=cardRepository.findFirstByUser_IdAndWord_Id(user, word);
        if (save==null){
            save =new Card(user,word);
        }
        save.setStudied(Boolean.TRUE);
        cardRepository.save(save);

    }


}
