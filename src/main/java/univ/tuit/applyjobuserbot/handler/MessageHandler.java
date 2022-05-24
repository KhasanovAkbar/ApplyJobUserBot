package univ.tuit.applyjobuserbot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.applyjobuserbot.domain.Apply;
import univ.tuit.applyjobuserbot.logic.SendMessageLogic;
import univ.tuit.applyjobuserbot.services.ApplyService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private ApplyService<Apply> applyService;

    @Autowired
    private SendMessageLogic sendMessageService;


    @Override
    public void choose(Message message) {
        String user_first_name = message.getChat().getFirstName();
        String user_last_name = message.getChat().getLastName();
        long user_id = message.getChat().getId();
        String message_text = message.getText();

        if (message.hasText()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text);

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
                    List<Apply> all = applyService.getAll();
                    Apply lastJob = new Apply();
                    for (Apply job : all) {
                        if (job.getUserId().equals(user_id)) {
                            lastJob = job;
                            break;
                        }
                    }
                    if (message.getFrom().getId().equals(applyService.findBy(user_id, lastJob.getApplyId()).getUserId())) {
                            sendMessageService.applyJob(message, lastJob.getApplyId());
                    }
            }
        } else if (message.hasDocument()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text);
            List<Apply> all = applyService.getAll();
            Apply lastJob = new Apply();
            for (Apply job : all) {
                if (job.getUserId().equals(user_id)) {
                    lastJob = job;
                    break;
                }
            }
            if (message.getFrom().getId().equals(applyService.findBy(user_id, lastJob.getApplyId()).getUserId())) {
                try {
                    sendMessageService. sendCV(message, lastJob.getApplyId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public static void log(String first_name, String last_name, String user_id, String txt) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
    }

}
