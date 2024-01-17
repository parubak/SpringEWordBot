package com.mygroup.springewordbot.controller;

import com.mygroup.springewordbot.config.BotConfig;
import com.mygroup.springewordbot.constant.ButtonId;
import com.mygroup.springewordbot.db.model.User;
import com.mygroup.springewordbot.service.CardService;
import com.mygroup.springewordbot.service.CommandService;
import com.mygroup.springewordbot.service.UserService;
import com.mygroup.springewordbot.service.WordService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Controller
//@Slf4j
public class TelegramBotController extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final CommandService commandService;
    private final WordService wordService;

    private final UserService userService;

    private final CardService cardService;



    public TelegramBotController(BotConfig botConfig, CommandService commandService, WordService wordService, UserService userService, CardService cardService) {

        this.botConfig = botConfig;
        this.commandService = commandService;
        this.wordService = wordService;
        this.userService = userService;
        this.cardService = cardService;


        List<BotCommand> botCommandList = new ArrayList<>();
        botCommandList.add(new BotCommand("/start", "Запуск бота"));
        botCommandList.add(new BotCommand("/sending", "Увімкнути/вимкнути повідомлення"));

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
            SendMessage sendMessage = new SendMessage();

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start" -> {
//                    Заповнити таблицю зі словами
//                    wordService.init();

                    sendMessage = commandService.startCommandReceived(update.getMessage().getChat());
                    sendMessage(chatId, sendMessage);
//

                    sendMessage = wordService.showWord(chatId,userService);

                }
                case "/sending" -> {
                    sendMessage=userService.sendingOnOff(chatId);
                }
                case "/t" -> {
                }
//                case "/help", "/help@SpringEWordBot" -> commandService.sendMessage(chatId
//                        , "info \n\n /test - Команда для теста функціонала ",this);
//                case "/test","/test@SpringEWordBot" -> {
//                    String еьщджи=EmojiParser.parseToUnicode(":gb:") + " - " + EmojiParser.parseToUnicode("\uD83C\uDDFA\uD83C\uDDE6");
//                    sendMessage(chatId, "Тест Тестович " +еьщджи +"\n\n"+
//                            "/licenseTest - Ліцензія на 10 днів\n"+
//                            "/licenseTest2 - Ліцензія закінчилась\n"+
//                            "/word - Приклад слова з перекладом та прикладом"
//
//                    );
//                    addButtonAndSendMassage("Кавяівяаа", chatId);
//                }
//                case "/id","/id@SpringEWordBot" -> sendMessage(chatId, "Ваш id в telegram:\n\n"+update.getMessage().getChat().getId());
//                case "/ok" ,"/ok@SpringEWordBot"-> sendMessage(chatId, "Все гуд!!!");
//                case "/license","/license@SpringEWordBot" ->
//                    sendMessage(chatId, userService.licenseOf(update.getMessage().getChat().getId()));
//                case "/licenseTest","/licenseTest@SpringEWordBot" ->
//                    sendMessage(chatId, userService.licenseOf(1L));
//                case "/licenseTest2","/licenseTest2@SpringEWordBot" ->
//                    sendMessage(chatId, userService.licenseOf(3L));
////                case "/word","/word@SpringEWordBot" -> sendMessage(chatId,wordService.showWord());
//                case "/in@SpringEWordBot" -> { long user=update.getMessage().getFrom().getId();
//
//                    sendMessage(user,"dscs"+user);


                default -> sendMessage = new SendMessage() {{
                    setText("Нет такой команди(");
                }};

            }

            sendMessage(chatId, sendMessage);

        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();


            switch (ButtonId.valueOf(callbackData)) {
                case WORD_NEXT -> sendMessage(chatId,wordService.showWord(chatId,userService));
                case WORD_OK -> {
                    User user=userService.findById(chatId);

                    cardService.learnedWord(user.getId(),user.getViewWord());

                    sendMessage(chatId,wordService.showWord(chatId,userService));
                }
//                case "wordNext" -> sendMessage(chatId,wordService.showWord());
            }
        }

    }

//    0/5 13,18 * * ?
//    @Scheduled(cron = "0 0/30 8-20 * * ?", zone = "Europe/Kiev")
    @Scheduled(cron = "0/20 * * * * ?")
    private void scheduledMethod() {

        for (User u : userService.findAllBySendingTrue()) {
            sendMessage(u.getId(), wordService.showWord(u.getId(),userService));
        }
    }



//    private void addButtonAndSendMassage(String text, long chatId) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setText(text);
//        sendMessage.setChatId(String.valueOf(chatId));
//
//        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
//        List<List<InlineKeyboardButton>> rowsListButton = new ArrayList<>();
//        List<InlineKeyboardButton> rowListButton = new ArrayList<>();
//
//        var inlineYes = new InlineKeyboardButton();
//        inlineYes.setCallbackData(YES);
//        inlineYes.setText("\uD83C\uDDFA\uD83C\uDDE6 OK");
//
//        var inlineNo = new InlineKeyboardButton();
//        inlineNo.setCallbackData(NO);
//        inlineNo.setText("\uD83C\uDDEC\uD83C\uDDE7 NO");
//
//        rowListButton.add(inlineYes);
//        rowListButton.add(inlineNo);
//        rowsListButton.add(rowListButton);
//        keyboardMarkup.setKeyboard(rowsListButton);
//        sendMessage.setReplyMarkup(keyboardMarkup);
//
//        sendMessageAndButton(sendMessage);
//    }


    public void sendMessage(long chatId, SendMessage message) {
        message.setChatId(String.valueOf(chatId));
        try {
            execute(message);
        } catch (TelegramApiException e) {
//            log.error(e.getMessage());
        }
    }
}
