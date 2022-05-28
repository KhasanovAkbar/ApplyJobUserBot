package univ.tuit.applyjobuserbot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.applyjobuserbot.domain.Candidate;
import univ.tuit.applyjobuserbot.logic.SendMessageLogic;
import univ.tuit.applyjobuserbot.services.CandidateService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private CandidateService<Candidate> candidateService;

    @Autowired
    private SendMessageLogic sendMessageService;


    @Override
    public SendMessage choose(Message message) {
        String user_first_name = message.getChat().getFirstName();
        String user_last_name = message.getChat().getLastName();
        long user_id = message.getChat().getId();
        String message_text = message.getText();
        SendMessage sm = null;

        if (message.hasText()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text);

            switch (message.getText()) {
                case "/start":
                    sm = sendMessageService.start(message);
                    //    cache.add(user);
                    break;

                case "/restart":
                    sm = sendMessageService.restart(message);
                    //     cache.add(user);
                    break;
                case "/help":
                    sm = sendMessageService.help(message);
                    break;
                case "Register":
                    sm = sendMessageService.apply(message);
                    break;
                default:
                    List<Candidate> all = candidateService.getAll();
                    Candidate lastJob = new Candidate();
                    for (Candidate job : all) {
                        if (job.getUserId().equals(user_id)) {
                            lastJob = job;
                            break;
                        }
                    }
                    if (message.getFrom().getId().equals(candidateService.findBy(user_id, lastJob.getCandidateId()).getUserId())) {
                        sm = sendMessageService.applyJob(message, lastJob.getCandidateId());
                    }
            }
        } else if (message.hasDocument()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text);
            List<Candidate> all = candidateService.getAll();
            Candidate lastJob = new Candidate();
            for (Candidate job : all) {
                if (job.getUserId().equals(user_id)) {
                    lastJob = job;
                    break;
                }
            }
            Long userId = candidateService.findBy(user_id, lastJob.getCandidateId()).getUserId();
            if (message.getFrom().getId().equals(userId)) {
                try {
                    sm = sendMessageService.sendCV(message, lastJob.getCandidateId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sm;
    }

    public static void log(String first_name, String last_name, String user_id, String txt) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
    }

}
