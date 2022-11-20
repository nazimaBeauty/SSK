package com.example.SSK.controller;

import com.example.SSK.model.InfoUser;
import com.example.SSK.repo.InofUserR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class TelegramBotClass extends TelegramLongPollingBot {
    String BOT_TOKEN;
    String BOT_USERNAME;

    @Autowired
    InofUserR inofUserR;

    TelegramBotClass(@Value("${bot.BOT_TOKEN}") String BOT_TOKEN, @Value("${bot.BOT_USERNAME}") String BOT_USERNAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_USERNAME = BOT_USERNAME;
        List<BotCommand> menu = new ArrayList<>();
        menu.add(new BotCommand("/start", "О нас"));
        menu.add(new BotCommand("/prices", "Цены"));
        menu.add(new BotCommand("/certificate", "Подарочные сертификаты"));
        menu.add(new BotCommand("/timetable", "Расписание"));
        menu.add(new BotCommand("/arsenal", "Арсенал"));
        menu.add(new BotCommand("/contact", "Контакты"));
        try {
            this.execute(new SetMyCommands(menu, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            System.out.println("error");
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/start":
                    aboutUs(update);
                    break;
                case "/prices":

                    break;
                case "/certificate":
                    break;
                case "/timetable":
                    break;
                case "/arsenal":
                    break;
                case "/whither":
                    break;
                case "/contact":
                    break;
            }
        } else if (update.hasCallbackQuery()) {
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
        }
    }

    private void aboutUs(Update update) {
        infoUser(update);

        String message = "Обучение граждан обращению с оружием\nВ Sport Shooting Federation KGZ каждый найдет себе развлечение по вкусу: Вы сможете пострелять из различных видов оружия, обучиться безопасному обращению с ним и повысить свои навыки в стрельбе. Мы проводим обучение пулевой стрельбе и безопасному обращению с оружием для всех желающих!";
        String link = "https://www.youtube.com/watch?v=AYz3VHZEFcU";
        menuBottom("Добро пожаловать в Sport Shooting Federation KGZ\n " + "\n" + message + " " + link, update);
    }

    private void infoUser(Update update) {
        List<InfoUser> users;
        users = (List<InfoUser>) inofUserR.findAll();
        boolean checker = false;
        for (InfoUser user : users) {
            if (!user.getUserID().equals(update.getMessage().getChatId())) {
                checker = true;
                break;
            }
        }
        if (checker) {
            InfoUser infoUser = new InfoUser(update.getMessage().getChatId(), update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getUserName());
            inofUserR.save(infoUser);
        }
    }

    private void menuBottom(String s, Update update) {
        SendMessage message = new SendMessage();

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        message.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        KeyboardRow keyboardFourthRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Бронировать"));
        keyboardSecondRow.add(new KeyboardButton("Геолокация"));
        keyboardSecondRow.add(new KeyboardButton("Заказать звонок"));
        keyboardSecondRow.add(new KeyboardButton("Отзыв"));

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        keyboard.add(keyboardThirdRow);
        keyboard.add(keyboardFourthRow);

        replyKeyboardMarkup.setKeyboard(keyboard);


        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText(s);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
