package com.mygroup.springewordbot.service;

import com.mygroup.springewordbot.model.DBExempl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class UserService {
    @Autowired
    private DBExempl dbExempl;

    public SendMessage licenseOf(Long id) {
        SendMessage message = new SendMessage();
        for (var u : dbExempl.getUsers()) {
            if (id.equals(u.getId())) {
                message.setText("Діє до: " + u.getLicenseEnd().toString());
                return message;
            }
        }
        String messageText = "❌💸 Твій акаунт неактивний.\n" +
                "Передплати користування:";
        message.setText(messageText);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();


        InlineKeyboardButton license30 = new InlineKeyboardButton("💰 "+"1 місяць - 30 грн"+" 🇺🇦");
        license30.setCallbackData("license30");
        InlineKeyboardButton license75 = new InlineKeyboardButton("💰💰 "+"3 місяць - 75 грн" +" 🇺🇦");
        license75.setUrl("https://translate.google.com/");
        InlineKeyboardButton license125 = new InlineKeyboardButton("💰💰💰 "+"6 місяць - 125 грн"+" 🇺🇦");
        license125.setCallbackData("license125");
        InlineKeyboardButton license200 = new InlineKeyboardButton("💰💰💰💰 "+"12 місяць - 200 грн"+" 🇺🇦");
        license200.setUrl("https://translate.google.com/?hl=uk&sl=en&tl=uk&text=Жмякай динамік!🔊&op=translate");

        List<List<InlineKeyboardButton>> rowsListButton = new ArrayList<>() {{
            add(new ArrayList<>() {{
                add(license30);
            }});
            add(new ArrayList<>() {{
                add(license75);
            }});
            add(new ArrayList<>() {{
                add(license125);
            }});
            add(new ArrayList<>() {{
                add(license200);
            }});
        }};
        keyboardMarkup.setKeyboard(rowsListButton);
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }
}
