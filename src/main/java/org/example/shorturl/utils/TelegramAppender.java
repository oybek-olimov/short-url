package org.example.shorturl.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TelegramAppender extends AppenderBase<LoggingEvent> {

    private final String token = "7938073458:AAFggBMng80wZWOrMMt400obFE-01WxzHKo";
    private final TelegramBot telegramBot = new TelegramBot(token);

    public TelegramAppender() {
        addFilter(new Filter<>() {
            @Override
            public FilterReply decide(LoggingEvent loggingEvent) {
                return loggingEvent.getLevel().equals(Level.ERROR) ? FilterReply.ACCEPT : FilterReply.DENY;
            }
        });
    }

    /*@Override
    protected void append(LoggingEvent loggingEvent) {
        String chatID = "6333479586";
        SendMessage sendMessage = new SendMessage(chatID, loggingEvent.getFormattedMessage());
        sendMessage.parseMode(ParseMode.Markdown);
        telegramBot.execute(sendMessage);


    }*/
    @Override
    protected void append(LoggingEvent loggingEvent) {
        String chatID = "6333479586";
        SendMessage sendMessage = new SendMessage(chatID, loggingEvent.getFormattedMessage());
        sendMessage.parseMode(ParseMode.Markdown);

        var response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            System.err.println("Failed to send message to Telegram:");
            System.err.println("Error Code: " + response.errorCode());
            System.err.println("Description: " + response.description());
        } else {
            System.out.println("Message sent successfully to Telegram!");
        }
    }

}
