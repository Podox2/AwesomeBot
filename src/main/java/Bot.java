import model.Weather;
import model.WeatherApi;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    final static String BOT_TOKEN = "1083596615:AAHZ9OJYuqrYSkQ_hO5-ERvHgmL_udcbH0Q";
    final static String BOT_USER_NAME = "MyTestVebinarPodoBot";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
            System.out.println("nice");
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
            System.out.println("fok");
        }
    }

    public void sendMsg(Message message, String answer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(answer);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Weather weather = new Weather();
        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "push this":
                    sendMsg(message, "Oh, hi Mark!");
                    break;
                case "then this":
                    sendMsg(message, "Anyway, how is your sex life?");
                    break;
                case "/start":
                    sendMsg(message, "Enter the city to get a weather");
                    break;
                default:
                    try {
                        sendMsg(message, WeatherApi.getWeather(message.getText(), weather));
                    } catch (IOException e) {
                        sendMsg(message, "No city like this");
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow firstKeyBoardRow = new KeyboardRow();
        firstKeyBoardRow.add(new KeyboardButton("push this"));
        firstKeyBoardRow.add(new KeyboardButton("then this"));

        keyboardRowList.add(firstKeyBoardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
