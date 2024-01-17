package com.mygroup.springewordbot.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(length = 20)
    private long id;
    private String userName;
    private LocalDate licenseEnd;

    private boolean sending;

    private Long viewWord;

    @OneToMany(mappedBy ="user",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards;


}
