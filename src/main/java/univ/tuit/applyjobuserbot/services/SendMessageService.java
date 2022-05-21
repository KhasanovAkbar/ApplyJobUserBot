package univ.tuit.applyjobuserbot.services;



import java.io.IOException;

public interface SendMessageService<T> {

    void start(T t);

    void restart(T t);

    void apply(T t);

    void applyJob(T t, Integer id);

    void sendCV(T t, Integer id) throws IOException;

    void help(T t);
}
