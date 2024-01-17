package com.mygroup.springewordbot.config;

import com.mygroup.springewordbot.controller.TelegramBotController;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

//@Slf4j
@Component
public class BotInitializer {
    final
    TelegramBotController bot;

    public BotInitializer(TelegramBotController bot) {
        this.bot = bot;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi botsApi= new TelegramBotsApi(DefaultBotSession.class);
        try {
        botsApi.registerBot(bot);
        }catch (TelegramApiException e){
//            log.error("Ошибка! Класс - "+e.getClass()+"/n  Опис - "+ e.getMessage());
        }


    }

}
