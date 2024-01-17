package com.mygroup.springewordbot.service;

import com.mygroup.springewordbot.db.model.User;
import com.mygroup.springewordbot.db.repo.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Data
public class UserService {
    private final UserRepository userRepository;
    private final WordService wordService;
    private final CardService cardService;


    public UserService(UserRepository userRepository, WordService wordService, CardService cardService) {
        this.userRepository = userRepository;
        this.wordService = wordService;
        this.cardService = cardService;
    }

    public SendMessage licenseOf(Long id) {

        SendMessage message=new SendMessage();
        LocalDate licenseEnd= userRepository.findById(id).get().getLicenseEnd();


        LocalDate now=LocalDate.now();

        if (!now.isAfter(licenseEnd)){
            message.setText("Ваша ліцензія діє до: " + licenseEnd.toString());
            return message;

        }
        message.setText("❌💸 Твій акаунт неактивний.\n" + "Передплати користування:");

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

    public boolean saveUser(Chat chat) {
        if (!userRepository.existsById(chat.getId())){

            User user=new User();
            user.setId(chat.getId());
            user.setUserName(chat.getUserName());
            user.setLicenseEnd(LocalDate.now().plusDays(7));
            user.setSending(true);


            userRepository.save(user);
            return true;
        }

        return false;

    }

    public Collection<User> findAllBySendingTrue(){
        return userRepository.getAllBySendingTrue();
    }

    public void setViewById(Long userId, Long wordId) {

      User user= userRepository.findById(userId).get();
      user.setViewWord(wordId);
      userRepository.save(user);


    }

    public User findById(long chatId) {
        return userRepository.findById(chatId).get();
    }

    public SendMessage sendingOnOff(long chatId) {
        SendMessage message=new SendMessage();
        User user=userRepository.findById(chatId).get();
        if (user.isSending()){
            user.setSending(Boolean.FALSE);
            message.setText("Сповіщення вимкненно. \n\n" +
                    "Щоб увімкнути, повторіть команду /sending");

        }else {
            user.setSending(Boolean.TRUE);
            message.setText("Сповіщення увімкненно! \n\n" +
                    "Щоб вимкнути, повторіть команду /sending");
        }
        userRepository.save(user);
        return message;
    }
}
