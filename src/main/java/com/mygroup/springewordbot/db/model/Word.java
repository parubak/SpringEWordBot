package com.mygroup.springewordbot.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name ="words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 20)
    private Long id;
    private  String word;
    private String transcription;
    private String translation;
    private String example;
    private String exampleTranslation;

    @OneToMany(mappedBy ="word",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards;




}
