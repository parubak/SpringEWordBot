package com.mygroup.springewordbot.service;

import com.mygroup.springewordbot.config.BotConfig;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
//@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig botConfig;
    static final String YES = "YES";
    static final String NO = "NO";

    public TelegramBot(BotConfig botConfig) {

        this.botConfig = botConfig;
        List<BotCommand> botCommandList = new ArrayList<>();
        botCommandList.add(new BotCommand("/start", "Запуск бота"));
        botCommandList.add(new BotCommand("/help", "info"));
        botCommandList.add(new BotCommand("/ol", "Как делЫ?"));
        botCommandList.add(new BotCommand("/ok", "ок"));
        botCommandList.add(new BotCommand("/test", "test \uD83C\uDDFA\uD83C\uDDE6"));

        try {
            this.execute(new SetMyCommands(botCommandList, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public String getBotUsername() {
        return this.botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return this.botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String chatName = update.getMessage().getChat().getFirstName();

            switch (messageText) {
                case "/start" -> startCommandReceived(chatId, chatName);
                case "/help" -> sendMessage(chatId, "info \n'\n /test - Команда для теста функціонала ");
                case "/test" -> {
                    sendMessage(chatId, "Тест Тестович " +
                            EmojiParser.parseToUnicode(":gb:") + " - " + EmojiParser.parseToUnicode("\uD83C\uDDFA\uD83C\uDDE6")
                    );
                    addButtonAndSendMassage("1213", chatId);
                }
                case "/ok" -> sendMessage(chatId, "Все гуд!!!");
                default -> sendMessage(chatId, "Нет такой команди(");
            }
        } else if (update.hasCallbackQuery()) {

            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (callbackData.equals(YES)) {
                addButtonAndSendMassage("Daaa!", chatId);

            }else if (callbackData.equals(NO)){
                addButtonAndSendMassage("Пізда малята!", chatId);

            }

        }
    }

    private void addButtonAndSendMassage(String text, long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(chatId));

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsListButton = new ArrayList<>();
        List<InlineKeyboardButton> rowListButton = new ArrayList<>();

        var inlineYes = new InlineKeyboardButton();
        inlineYes.setCallbackData(YES);
        inlineYes.setText("\uD83C\uDDFA\uD83C\uDDE6 OK");

        var inlineNo = new InlineKeyboardButton();
        inlineNo.setCallbackData(NO);
        inlineNo.setText("\uD83C\uDDEC\uD83C\uDDE7 NO");

        rowListButton.add(inlineYes);
        rowListButton.add(inlineNo);
        rowsListButton.add(rowListButton);
        keyboardMarkup.setKeyboard(rowsListButton);
        sendMessage.setReplyMarkup(keyboardMarkup);

        sendMessageAndButton(sendMessage);
    }


    private void startCommandReceived(long chatId, String chatName) {

        String answer = "Дароу " + chatName + "!";
        sendMessage(chatId, answer);
//    log.info("Okkkkkkkk");
    }

    private void sendMessage(long chatId, String textMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textMessage);

        try {
            execute(message);
        } catch (TelegramApiException e) {
//            log.error(e.getMessage());
        }
    }

    private void sendMessageAndButton(SendMessage msg) {

        try {
            execute(msg);
        } catch (TelegramApiException e) {
//            log.error(e.getMessage());
        }
    }
}
