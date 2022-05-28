package univ.tuit.applyjobuserbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import univ.tuit.applyjobuserbot.processor.Processor;

@Component
public class BotMain extends TelegramWebhookBot {

    @Value("${telegram.bot.username}")
    private String username;

    @Value("${telegram.bot.token}")
    private String token;

    @Value("${telegram.bot.webHookPath}")
    private String botPath;

    @Autowired
    private Processor processor;

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return processor.processor(update);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotPath() {
        return botPath;
    }
}
