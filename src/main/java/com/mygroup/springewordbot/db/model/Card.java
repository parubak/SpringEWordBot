package com.mygroup.springewordbot.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 20)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "word_id")
    Word word;

    Boolean studied;


    public Card( Long user, Long word) {
        this.id=null;
        this.user=new User(){{setId(user);}};
        this.word=new Word(){{setId(word);}};
        this.studied=null;
    }
}
