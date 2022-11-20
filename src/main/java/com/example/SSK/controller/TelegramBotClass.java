package com.example.SSK.controller;

import com.example.SSK.model.InfoUser;
import com.example.SSK.model.UserMessages;
import com.example.SSK.repo.InofUserR;
import com.example.SSK.repo.UserMessageR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class TelegramBotClass extends TelegramLongPollingBot {
    String BOT_TOKEN;
    String BOT_USERNAME;
    String globalMessage = "";

    @Autowired
    InofUserR inofUserR;
    @Autowired
    UserMessageR userMessageR;

    TelegramBotClass(@Value("${bot.BOT_TOKEN}") String BOT_TOKEN, @Value("${bot.BOT_USERNAME}") String BOT_USERNAME) {
        this.BOT_TOKEN = BOT_TOKEN;
        this.BOT_USERNAME = BOT_USERNAME;
        List<BotCommand> menu = new ArrayList<>();
        menu.add(new BotCommand("/start", "О нас"));
        menu.add(new BotCommand("/prices", "Цены"));
        menu.add(new BotCommand("/certificate", "Подарочные сертификаты"));
        menu.add(new BotCommand("/timetable", "Расписание"));
        menu.add(new BotCommand("/club", "Как стать членом клуба"));
        menu.add(new BotCommand("/arsenal", "Арсенал оружии"));
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
        String temp;
        if (update.hasMessage() && update.getMessage().hasText()) {
            if (update.getMessage().getChatId() == /*667621439L*/55) {
                try {
                    execute(confirmMessage(update));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else {
                switch (update.getMessage().getText()) {
                    case "/start":
                        aboutUs(update);
                        break;
                    case "/prices":
                        temp = """
                                УСЛУГИ КЛУБА
                                Аренда галереи - Договорная
                                Хранение оружия - Договорная
                                Чистка оружия - Договорная
                                Пристрелка оружия - Договорная
                                Диагностика оружия - Договорная
                                Аренда шкафчика - Договорная
                                9×19 Luger ТПЗ - Договорная
                                9×18 БПЗ - Договорная
                                .40 S&B - Договорная
                                .45 ACP ТПЗ - Договорная
                                .22 LR S&B - Договорная
                                5,45×39 / 7,62×39 / .223Rem (5,56×45) - Договорная
                                12×76 - Договорная
                                .50 AE (Desert Eagle) - Договорная
                                                            
                                А курсы:
                                """;
                        sendMessage(update, temp);
                        sendPhoto(update.getMessage().getChatId(), "https://github.com/nazimaBeauty/SSK/blob/main/src/main/resources/static/img/1.jpg?raw=true");
                        sendPhoto(update.getMessage().getChatId(), "https://github.com/nazimaBeauty/SSK/blob/main/src/main/resources/static/img/1.jpg?raw=true");
                        sendMessage(update, """
                                !!!Всем участникам тренировочных занятий необходимо быть членами клуба.
                                Все участники тренировочных занятий обязаны следовать Правилам клуба и нести ответственность за их несоблюдение.
                                Выстрелы оплачиваются отдельно на рецепции.
                                Минимальная длительность тренировки 1 час.!!!""");
                        break;
                    case "/club":
                        temp = "ПРАВИЛА ПОСЕЩЕНИЯ";
                        sendMessage(update, temp);
                        sendDocument(update);
                        temp = """
                                "BIG GUNS"
                                * при 100% предоплате программы. Есть ограничения.
                                ✓ парковка
                                ✓ трансфер*     ✓ аренда стрелковой галереи
                                ✓ разнообразные мишени
                                ✓ сертификат    ✓ инструктор
                                ✓ очки и наушники
                                ✓ боевое оружие""";
                        sendMessage(update, temp);
                        break;
                    case "/certificate":
                        type(update.getMessage().getChatId());
                        break;
                    case "/timetable":

                        break;
                    case "/arsenal":
                        sendPhoto(update.getMessage().getChatId(), "https://github.com/nazimaBeauty/karvenBot/blob/master/src/main/resources/image.png?raw=true");
                        temp = """
                                АРСЕНАЛ КЛУБА
                                Стрелковый клуб располагает самым большим в Бишкеке арсеналом оружия различных марок, моделей и калибров""";
                        sendMessage(update, temp + "\n\nhttp://919.kg/#prsert");
                        break;
                    case "Геолокация":
                        sendMessage(update, "Мы находимся: ");
                        temp = "https://2gis.kg/bishkek/search/%D1%83%D0%BB.%D0%A7%D0%BE%D0%BA%D0%B0%D0%BD%D0%B0%20%D0%92%D0%B0%D0%BB%D0%B8%D1%85%D0%B0%D0%BD%D0%BE%D0%B2%D0%B0%202%2F14/geo/70030076166323822/74.677277%2C42.857861?m=74.677294%2C42.857851%2F17.7";
                        sendMessage(update, temp);
                        break;
                    case "/contact":
                        temp = """
                                Контакты: +996509020253
                                Понедельник - воскресенье
                                09:00-23:00
                                Перед посещением необходимо записаться.
                                                                
                                instagram: https://www.instagram.com/shooting_federation_kyrgyzstan/?hl=ru
                                                                
                                telegram: https://web.telegram.org/k/#@ssk_shooting_bot
                                                                
                                whatsapp: https://wa.link/gfoqva""";
                        sendMessage(update, temp);
                        break;
                    case "Отзыв":
                        temp = "Пожалуйста можете заполнить эту форму: ";
                        sendMessage(update, temp + "http://localhost:5000/");
                        break;
                    case "Заказать звонок":
                        sendMessage(update, "Скоро администратор свяжется.");
                        SendMessage message = new SendMessage();
                        message.setChatId(667621439L);
                        message.setText("Заказать звонок" + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getUserName());
                        try {
                            execute(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        sendMessage(update, "Скоро свяжемся, спасибо что выбрали нас)");
                        sendAdmin(update);
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(checkPodMenu(update.getCallbackQuery().getMessage().getChatId(), update.getCallbackQuery().getData()));
                sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
            List<PhotoSize> photos = update.getMessage().getPhoto();
            String f_id = Objects.requireNonNull(photos.stream().max(Comparator.comparing(PhotoSize::getFileSize)).orElse(null)).getFileId();
            getInfoAboutUser(update);
            sendPhoto(667621439, f_id);
            sendMessage(update, "Подождите пока модератор не проверить.");
            try {
                execute(approvalPage()); //одобрение
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private SendMessage approvalPage() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Одобрить");
        inlineKeyboardButton1.setCallbackData("Одобрить");
        inlineKeyboardButton2.setText("Не одобрить");
        inlineKeyboardButton2.setCallbackData("Не одобрить");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(667621439L);
        sendMessage.setText("Одобрите или нет?");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    private void getInfoAboutUser(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(Long.valueOf(667621439));
        message.setText(update.getMessage().getChatId() + " " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getUserName());
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendAdmin(Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(667621439L);
        message.setText(update.getMessage().getText());
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserMessages infoUser = new UserMessages(update.getMessage().getFrom().getUserName(), update.getMessage().getFrom().getFirstName(), update.getMessage().getText(), update.getMessage().getChatId());
        userMessageR.save(infoUser);

    }

    private SendMessage confirmMessage(Update update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Отправить");
        inlineKeyboardButton1.setCallbackData("Отправить");
        inlineKeyboardButton2.setText("Отмена");
        inlineKeyboardButton2.setCallbackData("Отмена");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Точно вы хотите отправить?");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        globalMessage = update.getMessage().getText();
        return sendMessage;
    }

    private String checkPodMenu(Long chatId, String data) {
        String s = "";
        switch (data) {
            case "onlineC" ->
                    s = "Пополните баланс на счет 1180000098542005 и отправьте чек или звоните на +996509020253";
            case "offlineC" -> s = "Звоните на +996509020253";
            case "Отправить" -> s = adminSend(globalMessage);
            case "Отмена" -> s = "Отменено";
            case "allCe" -> {
                s = "Все сертификаты";
                sendPhoto(chatId, "https://github.com/nazimaBeauty/photoCarven/blob/main/images/image.png?raw=true");
                try {
                    execute(certificate(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            case "studyCe" -> {
                s = "Обучение на оружие";
                sendPhoto(chatId, "https://github.com/nazimaBeauty/temp.github.com/blob/main/image.png?raw=true");
                try {
                    execute(certificate(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            case "hmetr" -> {
                s = "100 метров";
                sendPhoto(chatId, "https://github.com/nazimaBeauty/temp.github.com/blob/main/images/image.png?raw=true");
                try {
                    execute(certificate(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            case "twmetr" -> {
                s = "25 метров";
                sendPhoto(chatId, "https://github.com/nazimaBeauty/karvenBot/blob/master/image.png?raw=true");
                try {
                    execute(certificate(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            case "withDepo" -> {
                s = "С депозитом";
                sendPhoto(chatId, "https://github.com/nazimaBeauty/karvenBot/blob/master/src/image.png?raw=true");
                try {
                    execute(certificate(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            case "shootPro" -> {
                s = "Стрелковые программы";
                sendPhoto(chatId, "https://github.com/nazimaBeauty/karvenBot/blob/master/src/main/image.png?raw=true");
                try {
                    execute(certificate(chatId));
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            case "Одобрить" -> {
                sendMessageByID(chatId, "Одобрено");
                s = new Date() + ", id: " + chatId;
            }
            case "Не одобрить" -> s = "Не одобрено";
            default -> s = "";
        }
        return s;
    }

    private void type(Long id) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("Все сертификаты");
        inlineKeyboardButton1.setCallbackData("allCe");
        inlineKeyboardButton2.setText("Обучение на оружие");
        inlineKeyboardButton2.setCallbackData("studyCe");
        inlineKeyboardButton3.setText("100 метров");
        inlineKeyboardButton3.setCallbackData("hmetr");
        inlineKeyboardButton4.setText("25 метров");
        inlineKeyboardButton4.setCallbackData("twmetr");
        inlineKeyboardButton5.setText("С депозитом");
        inlineKeyboardButton5.setCallbackData("withDepo");
        inlineKeyboardButton6.setText("Стрелковые программы");
        inlineKeyboardButton6.setCallbackData("shootPro");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow2.add(inlineKeyboardButton3);
        keyboardButtonsRow2.add(inlineKeyboardButton4);
        keyboardButtonsRow3.add(inlineKeyboardButton5);
        keyboardButtonsRow3.add(inlineKeyboardButton6);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText("Сертификаты с депозитной системой");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private String adminSend(String globalMessage) {
        List<InfoUser> infoUsers;
        infoUsers = (List<InfoUser>) inofUserR.findAll();
        for (InfoUser infoUser : infoUsers) {
            sendMessageByID(infoUser.getUserID(), globalMessage);
        }
        return "done";
    }

    private void sendMessageByID(Long userID, String globalMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(userID);
        message.setText(globalMessage);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SendMessage certificate(Long update) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("электронный сертификат на программу");
        inlineKeyboardButton1.setCallbackData("onlineC");
        inlineKeyboardButton2.setText("электронный сертификат на номинал");
        inlineKeyboardButton2.setCallbackData("offlineC");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow1);
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update);
        sendMessage.setText("Подарочные сертификаты");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    private void sendDocument(Update update) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(update.getMessage().getChatId());
        InputFile inputFile = new InputFile("http://karven.kg/images/news/2018/05/booking-order-rules.pdf");
        sendDocument.setDocument(inputFile);
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage(Update update, String s) {
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText(s);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPhoto(long chat_id, String s) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chat_id);
        InputFile inputFile = new InputFile(s);
        sendPhoto.setPhoto(inputFile);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void aboutUs(Update update) {
        infoUser(update);

        String message = "Обучение граждан обращению с оружием\nВ Sport Shooting Federation KGZ каждый найдет себе развлечение по вкусу: Вы сможете пострелять из различных видов оружия, обучиться безопасному обращению с ним и повысить свои навыки в стрельбе. Мы проводим обучение пулевой стрельбе и безопасному обращению с оружием для всех желающих!";
        String link = "https://www.youtube.com/watch?v=AYz3VHZEFcU";
        menuBottom("Добро пожаловать в Sport Shooting Federation KGZ\n " + "\n" + message + " " + link, update);
    }

    private void infoUser(Update update) {
        InfoUser infoUser = new InfoUser(update.getMessage().getChatId(), update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getUserName());
        inofUserR.save(infoUser);
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
        keyboardFirstRow.add(new KeyboardButton("Записаться"));
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
