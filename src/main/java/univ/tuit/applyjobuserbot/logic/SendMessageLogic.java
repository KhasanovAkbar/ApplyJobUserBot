package univ.tuit.applyjobuserbot.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import univ.tuit.applyjobuserbot.domain.Applied;
import univ.tuit.applyjobuserbot.domain.Candidate;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.Requirement;
import univ.tuit.applyjobuserbot.domain.enums.ApplyEnum;
import univ.tuit.applyjobuserbot.domain.enums.State;
import univ.tuit.applyjobuserbot.messageSender.MessageSender;
import univ.tuit.applyjobuserbot.services.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SendMessageLogic implements SendMessageService<Message> {


    @Autowired
    JobService<Jobs> jobService;

    @Autowired
    RequirementService requirementService;

    @Autowired
    CandidateService<Candidate> candidateService;

    @Autowired
    ApplyService<Applied> applyService;

    private final MessageSender messageSender;

    @Value("${telegram.bot.token}")
    private String token;

    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    ArrayList<KeyboardRow> keyboardRow = new ArrayList<>();

    Candidate candidate = new Candidate();

    Applied applied = new Applied();

    public SendMessageLogic(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    @Override
    public SendMessage start(Message message) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Hello " + message.getFrom().getFirstName() + "\nLorem ipsum");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            keyboardRow.clear();
            sendMessage.setReplyMarkup(buttons());
            messageSender.sendMessage(sendMessage);
            log.info("bot started");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public SendMessage restart(Message message) {
        //restart bot
        log.info("bot restarted");
        start(message);
        return null;
    }

    @Override
    public SendMessage apply(Message message) {
        try {
            long chat_id = message.getChatId();
            candidate = new Candidate();
            info(message, chat_id);
            SendMessage sm = new SendMessage();
            sm.setText("Ish joyi topish uchun ariza berish\n" +
                    "\n" +
                    "Hozir sizga bir necha savollar beriladi. \n" +
                    "Har biriga javob bering. \n" +
                    "Oxirida agar hammasi to`g`ri bo`lsa, HA tugmasini bosing va arizangiz Adminga yuboriladi.");
            sm.setChatId(String.valueOf(message.getChatId()));
            messageSender.sendMessage(sm);

            messageSender.sendMessage(SendMessage.builder()
                    .text("Ish id:")
                    .chatId(String.valueOf(message.getChatId()))
                    .build());
            candidate.setJobId(message.getText());
            candidate.setIsJobId(1);
            candidateService.apply(candidate);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public SendMessage applyJob(Message message, Integer id) {
        long chat_id = message.getChatId();
        candidate = new Candidate();
        candidate = candidateService.findBy(chat_id, id);
        if (candidate.getIsJobId() == 1) {
            Jobs byJobId = jobService.findByJobId(message.getText());
            if (byJobId == null) {
                messageSender.sendMessage(SendMessage
                        .builder().text("Bunday ish id topilmadi.")
                        .chatId(String.valueOf(chat_id))
                        .build());
                messageSender.sendMessage(SendMessage.builder()
                        .text("Ish id:")
                        .chatId(String.valueOf(message.getChatId()))
                        .build());

            } else {
                applied = new Applied();
                applied = applyService.findByJobIdAndCandidateId(byJobId.getJobId(), candidate.getUserId());

                if (applied != null) {
                    if (!applied.getStatus().equals(ApplyEnum.REJECTED.name())) {
                        messageSender.sendMessage(SendMessage.builder().text(
                                        "\uD83C\uDF93 Id: " + byJobId.getJobId() + "\n" +
                                                "\uD83C\uDFE2 Idora: " + byJobId.getCompanyName() + "\n" +
                                                "\uD83D\uDCDA Texnologiya: " + byJobId.getTechnology() + "\n" +
                                                "\uD83C\uDF10 Hudud: " + byJobId.getTerritory())
                                .parseMode("HTML")
                                .chatId(String.valueOf(message.getChatId()))
                                .build());
                        messageSender.sendMessage(SendMessage.builder()
                                .text("Ish beruvchi tomonidan qo'yilgan talablar")
                                .parseMode("HTML")
                                .chatId(String.valueOf(message.getChatId()))
                                .build());
                        applied = new Applied();
                        applied.setJobId(byJobId.getJobId());
                        applied.setStatus(ApplyEnum.NONE.name());
                        applied.setCandidateId(byJobId.getUserId());
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        String format = dateFormat.format(date);
                        applied.setRegistrationTime(format);
                        applyService.create(applied);

                        candidate.setIsRequirement(1);
                        candidate.setIsJobId(0);
                        candidate.setJobId(message.getText());
                    } else {
                        messageSender.sendMessage(SendMessage.builder().text(
                                        "\uD83C\uDF93 Id: " + byJobId.getJobId() + "\n" +
                                                "\uD83C\uDFE2 Idora: " + byJobId.getCompanyName() + "\n" +
                                                "\uD83D\uDCDA Texnologiya: " + byJobId.getTechnology() + "\n" +
                                                "\uD83C\uDF10 Hudud: " + byJobId.getTerritory())
                                .parseMode("HTML")
                                .chatId(String.valueOf(message.getChatId()))
                                .build());

                        messageSender.sendMessage(SendMessage.builder()
                                .text("Siz ish beruvchining talablariga javob bermaysiz")
                                .parseMode("HTML")
                                .chatId(String.valueOf(message.getChatId()))
                                .build());
                        candidate.setIsRequirement(0);
                        candidate.setIsName(0);
                    }
                } else {
                    messageSender.sendMessage(SendMessage.builder().text(
                                    "\uD83C\uDF93 Id: " + byJobId.getJobId() + "\n" +
                                            "\uD83C\uDFE2 Idora: " + byJobId.getCompanyName() + "\n" +
                                            "\uD83D\uDCDA Texnologiya: " + byJobId.getTechnology() + "\n" +
                                            "\uD83C\uDF10 Hudud: " + byJobId.getTerritory())
                            .parseMode("HTML")
                            .chatId(String.valueOf(message.getChatId()))
                            .build());
                    messageSender.sendMessage(SendMessage.builder()
                            .text("Ish beruvchi tomonidan qo'yilgan talablar")
                            .parseMode("HTML")
                            .chatId(String.valueOf(message.getChatId()))
                            .build());
                    applied = new Applied();
                    applied.setJobId(byJobId.getJobId());
                    applied.setStatus(ApplyEnum.NONE.name());
                    applied.setCandidateId(candidate.getUserId());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String format = dateFormat.format(date);
                    applied.setRegistrationTime(format);
                    applyService.create(applied);

                    candidate.setIsRequirement(1);
                    candidate.setIsJobId(0);
                    candidate.setJobId(message.getText());
                }
            }
        }

        if (candidate.getIsRequirement() == 1 && message.getText().equals(candidate.getJobId())) {
            Applied apply;
            apply = applyService.findByJobIdAndCandidateId(candidate.getJobId(), candidate.getUserId());
            List<Requirement> byJob = requirementService.findByJob(jobService.findByJobId(candidate.getJobId()));

            for (int i = 0; i < byJob.size(); i++) {
                if (i == apply.getRequirementCount()) {
                    getRequirement(message.getChatId(), byJob.get(i).getName());
                    apply.setRequirementCount(apply.getRequirementCount() + 1);
                    applyService.update(apply);
                    break;
                }
            }
        }

        else if (candidate.getIsRequirement() == 1 && message.getText().equals("Ha")) {
            Applied apply;
            apply = applyService.findByJobIdAndCandidateId(candidate.getJobId(), candidate.getUserId());
            List<Requirement> byJob = requirementService.findByJob(jobService.findByJobId(candidate.getJobId()));
            for (int i = 0; i < byJob.size(); i++) {
                if (i == apply.getRequirementCount()) {
                    getRequirement(message.getChatId(), byJob.get(i).getName());

                    apply.setRequirementCount(apply.getRequirementCount() + 1);
                    apply.setStatus(ApplyEnum.NOT_FINISHED.name());
                    applyService.update(apply);
                    break;
                } else if (apply.getRequirementCount() == byJob.size() && message.getText().equals("Ha")) {
                    candidate.setIsName(1);
                    keyboardRow.clear();
                }
            }

        }

        else if (candidate.getIsRequirement() == 1 && message.getText().equals("Yo'q")) {
            applied = new Applied();
            applied = applyService.findByJobIdAndCandidateId(candidate.getJobId(), candidate.getUserId());
            keyboardRow.clear();
            messageSender.sendMessage(SendMessage
                    .builder().text("Tugadi")
                    .chatId(String.valueOf(chat_id))
                    .replyMarkup(buttons())
                    .build());

            applied.setStatus(ApplyEnum.REJECTED.name());
            applyService.update(applied);
        }

        if (candidate.getName().equals("Register") && candidate.getIsName() == 1) {
            applied = new Applied();
            applied = applyService.findByJobIdAndCandidateId(candidate.getJobId(), candidate.getUserId());
            messageSender.sendMessage(SendMessage
                    .builder().text("Ism, familiyangizni kiriting?")
                    .chatId(String.valueOf(chat_id))
                    .build());
            candidate.setName(message.getText());
            candidate.setIsAge(1);
            candidate.setIsRequirement(0);
            applied.setStatus(ApplyEnum.ACCEPTED.name());
            applyService.update(applied);

        }

        else if (candidate.getAge().equals("Register") && candidate.getIsAge() == 1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDD51 Yosh: \n" +
                            "\n" +
                            "Yoshingizni kiriting?\n" +
                            "Masalan, 19")
                    .chatId(String.valueOf(chat_id))
                    .replyMarkup(buttons())
                    .build());
            candidate.setName(message.getText());
            candidate.setAge(message.getText());
            candidate.setIsPhone(1);

        }

        else if (candidate.getPhoneNumber().equals("Register") && candidate.getIsPhone() == 1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDCDE Aloqa: \n" +
                            "\n" +
                            "Bog`lanish uchun raqamingizni kiriting?\n" +
                            "Masalan, +998 90 123 45 67")
                    .chatId(String.valueOf(chat_id))
                    .build());
            candidate.setAge(message.getText());
            candidate.setPhoneNumber(message.getText());
            candidate.setIsFilePath(1);

        }

        else if (candidate.getFilePath().equals("Register") && candidate.getIsFilePath() == 1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDCC1 Fayl: \n" +
                            "\n" +
                            "CV yuboring (pdf, docx, doc)")
                    .chatId(String.valueOf(chat_id))
                    .build());
            candidate.setPhoneNumber(message.getText());
            candidate.setFilePath(message.getText());

        }

        else if (message.getText().equals("Ha") && candidate.getState().equals(State.CHECKED.toString())) {

            applied = new Applied();
            applied = applyService.findByJobIdAndCandidateId(candidate.getJobId(), candidate.getUserId());
            SendMessage sm = new SendMessage();
            sm.setText("Ma'lumotlaringiz ish beruvchiga yuborildi");
            sm.setParseMode("HTML");
            sm.setChatId(String.valueOf(message.getChatId()));
            candidate.setState(State.COMPLETED.toString());
            keyboardRow.clear();
            applied.setStatus(ApplyEnum.FINISHED.name());
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);
            applyService.update(applied);

        }

        else if (message.getText().equals("Yo'q") && candidate.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Qabul qilinmadi" +
                    "\n/start so`zini bosing. E'lon berish qaytadan boshlanadi");
            sm.setChatId(String.valueOf(message.getChatId()));
            candidate.setState(State.DENIED.toString());
            candidate.setIsJobId(0);
            candidate.setIsName(0);
            candidate.setIsAge(0);
            candidate.setIsPhone(0);
            candidate.setIsFilePath(0);
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);
        }
        candidateService.update(candidate);
        return null;
    }

    @Override
    public SendMessage sendCV(Message message, Integer id) throws IOException {

        long chat_id = message.getChatId();
        Document document = message.getDocument();
        candidate = candidateService.findBy(chat_id, id);
        String fileId = document.getFileId();

        String urlContents = getUrlContents("https://api.telegram.org/bot" + token + "/getFile?file_id=" + fileId);
        candidate.setFilePath(urlContents);
        candidate.setToken(token);

        if (candidate.getState().equals(State.NONE.toString()) && candidate.getIsFilePath() == 1) {
            markup = new ReplyKeyboardMarkup();
            keyboardRow = new ArrayList<>();
            messageSender.sendMessage(SendMessage.builder()
                    .text("\uD83C\uDF93 Id: " + candidate.getJobId() + "\n" +
                            "\uD83D\uDC68\u200D\uD83D\uDCBC Xodim: " + candidate.getName() + "\n" +
                            "\uD83D\uDD51 Yosh: " + candidate.getAge() + "\n" +
                            "\uD83C\uDDFA\uD83C\uDDFF Telegram: @" + candidate.getUsername() + "\n" +
                            "\uD83D\uDCDE Aloqa: " + candidate.getPhoneNumber())
                    .parseMode("HTML")
                    .chatId(String.valueOf(message.getChatId()))
                    .build());

            SendMessage sm = new SendMessage();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("Ha");
            row1.add(KeyboardButton.builder().text("Yo'q").build());
            keyboardRow.add(row1);
            markup.setKeyboard(keyboardRow);
            markup.setOneTimeKeyboard(true);
            markup.setResizeKeyboard(true);
            sm.setText("Barcha ma'lumotlar to'g'rimi?");
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setReplyMarkup(markup);
            messageSender.sendMessage(sm);
            candidate.setState(State.CHECKED.toString());
            log.info("File was uploaded");
        }
        candidateService.update(candidate);
        return null;
    }

    @Override
    public SendMessage help(Message message) {
        log.info("info");
        return SendMessage
                .builder().text("Bu bot bir narsa bir narsa")
                .chatId(String.valueOf(message.getChatId()))
                .build();
    }

    private ReplyKeyboard buttons() {

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Register");
        keyboardRow.add(row1);

        markup.setKeyboard(keyboardRow);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        return markup;
    }

    public void info(Message message, long user_id) {
        String username = message.getFrom().getUserName();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String format = dateFormat.format(date);

        candidate.setUserId(user_id);
        candidate.setUsername(username);
        candidate.setState(State.NONE.toString());
        candidate.setRegistrationTime(format);
    }

    private static String getUrlContents(String theUrl) {
        String path = null;
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String[] split = bufferedReader.readLine().split(":");
            String a = split[split.length - 1];
            path = a.substring(1, a.length() - 3);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }

    void getRequirement(Long chat_id, String requirement) {
        messageSender.sendMessage(SendMessage
                .builder().text(requirement)
                .chatId(String.valueOf(chat_id))
                .build());
        candidate.setIsRequirement(1);
        markup = new ReplyKeyboardMarkup();
        keyboardRow = new ArrayList<>();

        SendMessage sm = new SendMessage();
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Ha");
        row1.add(KeyboardButton.builder().text("Yo'q").build());
        keyboardRow.add(row1);
        markup.setKeyboard(keyboardRow);
        markup.setOneTimeKeyboard(true);
        markup.setResizeKeyboard(true);
        sm.setText("Ushbu talabga javob berasizmi?");
        sm.setChatId(String.valueOf(chat_id));
        sm.setReplyMarkup(markup);
        messageSender.sendMessage(sm);
    }
}
