package com.mygroup.springewordbot.service;


import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;


@Service
public class CommandService {

    UserService userService;

    public CommandService(UserService userService) {
        this.userService = userService;
    }


    public SendMessage startCommandReceived(Chat chat) {
        SendMessage message;
        if (!userService.saveUser(chat)) {
            message = userService.licenseOf(chat.getId());

            message.setText("Ви вже зареєстровані!\n\n" + message.getText());



        } else {

            message = userService.licenseOf(chat.getId());
            message.setText("Вітаємо з реєстрацією 🥳" + chat.getFirstName() + " !\n\n"
                    + "Вам надоно безкоштовне користування ботом на 7 днів.\n"
                    + message.getText()
            );


        }

        return message;
    }

}
