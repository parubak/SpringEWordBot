package com.mygroup.springewordbot.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Word {

    private  String word;
    private String transcription;
    private String translation;
    private String example;
    private String exampleTranslation;





}
