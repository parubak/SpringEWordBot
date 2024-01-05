package com.mygroup.springewordbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    private long id;

    private LocalDate licenseEnd;

}
