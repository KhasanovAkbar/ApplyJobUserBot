package univ.tuit.applyjobuserbot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.applyjobuserbot.cache.Cache;
import univ.tuit.applyjobuserbot.domain.Apply;
import univ.tuit.applyjobuserbot.services.SendMessageServiceImpl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private final Cache<Apply> cache;

    @Autowired
    private SendMessageServiceImpl sendMessageService;

    public MessageHandler(Cache<Apply> cache) {
        this.cache = cache;
    }


    @Override
    public void choose(Message message) {
        String user_first_name = message.getChat().getFirstName();
        String user_last_name = message.getChat().getLastName();
        long user_id = message.getChat().getId();
        String message_text = message.getText();

        if (message.hasText()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, message_text);

            switch (message.getText()) {
                case "/start":
                    sendMessageService.start(message);
                    //    cache.add(user);
                    break;

                case "/restart":
                    sendMessageService.restart(message);
                    //     cache.add(user);
                    break;
                case "/help":
                    sendMessageService.help(message);
                    break;
                case "Register":
                    sendMessageService.apply(message);
                    break;
                default:
                    List<Apply> all = cache.getAll();
                    Apply lastJob = new Apply();
                    for (Apply job : all) {
                        if (job.getUserId().equals(user_id)) {
                            lastJob = job;
                            break;
                        }
                    }
                    if (message.getFrom().getId().equals(cache.findBy(user_id, lastJob.getApplyId()).getUserId())) {
                        if (lastJob.isJobId())
                            sendMessageService.applyJob(message, lastJob.getApplyId());
                    }
            }
        } else if (message.hasDocument()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, message_text);
            List<Apply> all = cache.getAll();
            Apply lastJob = new Apply();
            for (Apply job : all) {
                if (job.getUserId().equals(user_id)) {
                    lastJob = job;
                    break;
                }
            }
            if (message.getFrom().getId().equals(cache.findBy(user_id, lastJob.getApplyId()).getUserId())) {
                try {
                    sendMessageService. sendCV(message, lastJob.getApplyId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

}
