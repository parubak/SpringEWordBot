package com.mygroup.springewordbot.service;

import com.mygroup.springewordbot.model.DBExempl;
import com.mygroup.springewordbot.model.Word;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Data
public class WordService {
    @Autowired
    private DBExempl dbExempl;

    SendMessage showWord(){
        SendMessage message = new SendMessage();
        int size =dbExempl.getWords().size();

        Random rand = new Random();
        Word rWord=dbExempl.getWords().get(rand.nextInt(size));

        String messageText = "🇬🇧 "+rWord.getWord()+" - ["+rWord.getTranscription()+"] - "+ rWord.getTranslation()+" 🇺🇦\n\n" +
                "🗣 "+rWord.getExample()+"\n"+
                "‍🧑‍🏫 "+rWord.getExampleTranslation();

        message.setText(messageText);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton wordOk = new InlineKeyboardButton("✅ "+"Вивчив");
        wordOk.setCallbackData("wordOk");
        InlineKeyboardButton wordNext = new InlineKeyboardButton("➡️ "+"Наступне");
        wordNext.setCallbackData("wordNext");

        List<List<InlineKeyboardButton>> rowsListButton = new ArrayList<>() {{
            add(new ArrayList<>() {{
                add(wordOk);
                add(wordNext);
            }});

        }};
        keyboardMarkup.setKeyboard(rowsListButton);
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
