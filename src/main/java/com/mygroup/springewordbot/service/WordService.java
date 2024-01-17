package com.mygroup.springewordbot.service;


import com.mygroup.springewordbot.constant.ButtonId;
import com.mygroup.springewordbot.db.model.Word;
import com.mygroup.springewordbot.db.repo.UserRepository;
import com.mygroup.springewordbot.db.repo.WordRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class WordService {
    final WordRepository wordRepository;
    final UserRepository userRepository;


    public WordService(WordRepository wordRepository, UserRepository userRepository) {
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
    }

    public void init() {
        List<Word> words = new ArrayList<>();

        words.add(new Word(null, "Adventure", "ədˈvɛn.tʃər", "Пригода",
                "Going on a solo hiking trip was a thrilling adventure for Sarah.",
                "Подорож у гори в одиночестві була захопливою пригодою для Сари.",null));
        words.add(new Word(null, "Efficient", "ɪˈfɪʃ.ənt", "Ефективний",
                "Using a programmable thermostat is an efficient way to manage energy consumption at home.",
                "Використання програмованого термостата - це ефективний спосіб керування споживанням енергії вдома.",null));
        words.add(new Word(null, "Illuminate", "ɪˈluː.mɪ.neɪt", "Підсвічувати",
                "The streetlights helped illuminate the path during the dark night.",
                "Ліхтарі допомогли підсвітити стежку під час темної ночі.",null));
        words.add(new Word(null, "Resilient", "rɪˈzɪl.jənt", "Стійкий, витривалий",
                "Despite facing many challenges, the community showed a resilient spirit and quickly recovered.",
                "Незважаючи на багато труднощів, спільнота проявила стійкий дух і швидко відновилася.",null));
        words.add(new Word(null, "Versatile", "ˈvɜː.sə.taɪl", "Універсальний",
                "A Swiss Army knife is a versatile tool that can be used for various purposes, such as cutting and opening bottles.",
                "Швейцарський армійський ніж - це універсальний інструмент, який можна використовувати для різних цілей, таких як різання і відкривання пляшок.",null));
        wordRepository.saveAll(words);

    }

    public Word firstWord(){
        return wordRepository.findTopByOrderById();
    }

    public SendMessage showWord() {
        SendMessage message = new SendMessage();
        long count = wordRepository.count();

        Random rand = new Random();
        Word rWord = wordRepository.findById(rand.nextLong(count) + 1L).get();

        String messageText = "🇬🇧 " + rWord.getWord() + " - [" + rWord.getTranscription() + "] - " + rWord.getTranslation() + " 🇺🇦\n\n" +
                "🗣 " + rWord.getExample() + "\n" +
                "‍🧑‍🏫  ||" + rWord.getExampleTranslation()+"||";

        message.setText(messageText);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton wordOk = new InlineKeyboardButton("✅ " + "Вивчив");
        wordOk.setCallbackData(ButtonId.WORD_OK.name());
        InlineKeyboardButton wordNext = new InlineKeyboardButton("➡️ " + "Наступне");
        wordNext.setCallbackData(ButtonId.WORD_NEXT.name());

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

    public SendMessage showWord(Long userId, UserService userService) {

        LocalDate licenseEnd= userRepository.findById(userId).get().getLicenseEnd();


        LocalDate now=LocalDate.now();

        if (now.isAfter(licenseEnd)){
            return new SendMessage();
        }

        SendMessage message = new SendMessage();


        List<Word> wordList=wordRepository.top3WordsByUserId(userId);

        if (wordList.size()==0){
            wordRepository.reserById(userId);
            message.setText("Вітаємо! Ви вивчили всі слова!\n" +
                    "Закріпіть пройдене!Почніть спочатку /start");
            return message;
        }

        Random rand = new Random();
        Word rWord = wordList.get(rand.nextInt(wordList.size()));

        userService.setViewById(userId, rWord.getId());




        String messageText = "🇬🇧 " + rWord.getWord() + " - [" + rWord.getTranscription() + "] - " + rWord.getTranslation() + " 🇺🇦\n\n" +
                "🗣 " + rWord.getExample() + "\n" +
                "‍🧑‍🏫 " + rWord.getExampleTranslation();

        message.setText(messageText);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton wordOk = new InlineKeyboardButton("✅ " + "Вивчив");
        wordOk.setCallbackData(ButtonId.WORD_OK.name());
        InlineKeyboardButton wordNext = new InlineKeyboardButton("➡️ " + "Наступне");
        wordNext.setCallbackData(ButtonId.WORD_NEXT.name());

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


    public Word findById(Long viewWord) {
        return wordRepository.findById(viewWord).get();
    }
}
