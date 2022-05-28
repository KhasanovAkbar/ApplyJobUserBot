package univ.tuit.applyjobuserbot.services;



import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

public interface SendMessageService<T> {

    SendMessage start(T t);

    SendMessage restart(T t);

    SendMessage apply(T t);

    SendMessage applyJob(T t, Integer id);

    SendMessage sendCV(T t, Integer id) throws IOException;

    SendMessage help(T t);
}
