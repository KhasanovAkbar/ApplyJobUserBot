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
import univ.tuit.applyjobuserbot.domain.Apply;
import univ.tuit.applyjobuserbot.domain.Jobs;
import univ.tuit.applyjobuserbot.domain.State;
import univ.tuit.applyjobuserbot.messageSender.MessageSender;
import univ.tuit.applyjobuserbot.services.ApplyService;
import univ.tuit.applyjobuserbot.services.JobService;
import univ.tuit.applyjobuserbot.services.RequirementService;
import univ.tuit.applyjobuserbot.services.SendMessageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
@Slf4j
public class SendMessageLogic implements SendMessageService<Message> {

    private final MessageSender messageSender;

    @Autowired
    JobService<Jobs> jobService;

    @Autowired
    RequirementService requirementService;

    @Autowired
    ApplyService<Apply> applyService;

    @Value("${telegram.bot.token}")
    private String token;

    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    ArrayList<KeyboardRow> keyboardRow = new ArrayList<>();

    static Apply apply = new Apply();


    public SendMessageLogic(MessageSender messageSender) {
        this.messageSender = messageSender;
    }


    @Override
    public void start(Message message) {
        try {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Hello " + message.getFrom().getFirstName() + "\nLorem ipsum");
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            keyboardRow.clear();
            sendMessage.setReplyMarkup(buttons());
            messageSender.sendMessage(sendMessage);
            log.info("Start bot");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void restart(Message message) {
        //restart bot
        start(message);

    }

    @Override
    public void apply(Message message) {
        try {
            long chat_id = message.getChatId();
            apply = new Apply();
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
            apply.setJobId(message.getText());
            apply.setIsJobId(1);
            applyService.apply(apply);
            log.info("Create user");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void applyJob(Message message, Integer id) {
        long chat_id = message.getChatId();
        apply = new Apply();
        apply = applyService.findBy(chat_id, id);
        if (apply.getIsJobId() == 1) {
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
                messageSender.sendMessage(SendMessage.builder().text(
                                "\uD83C\uDF93 Id: " + byJobId.getJobId() + "\n" +
                                        "\uD83C\uDFE2 Idora: " + byJobId.getCompanyName() + "\n" +
                                        "\uD83D\uDCDA Texnologiya: " + byJobId.getTechnology() + "\n" +
                                        "\uD83C\uDF10 Hudud: " + byJobId.getTerritory())
                        .parseMode("HTML")
                        .chatId(String.valueOf(message.getChatId()))
                        .build());
                apply.setIsName(1);
            }
        }
        if (apply.getName().equals("Register") && apply.getIsName() == 1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("Ism, familiyangizni kiriting?")
                    .chatId(String.valueOf(chat_id))
                    .build());
            apply.setJobId(message.getText());
            apply.setName(message.getText());
            apply.setIsAge(1);
            apply.setIsJobId(0);

        } else if (apply.getAge().equals("Register") && apply.getIsAge()== 1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDD51 Yosh: \n" +
                            "\n" +
                            "Yoshingizni kiriting?\n" +
                            "Masalan, 19")
                    .chatId(String.valueOf(chat_id))
                    .build());
            apply.setName(message.getText());
            apply.setAge(message.getText());
            apply.setIsPhone(1);

        } else if (apply.getPhoneNumber().equals("Register") && apply.getIsPhone()==1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDCDE Aloqa: \n" +
                            "\n" +
                            "Bog`lanish uchun raqamingizni kiriting?\n" +
                            "Masalan, +998 90 123 45 67")
                    .chatId(String.valueOf(chat_id))
                    .build());
            apply.setAge(message.getText());
            apply.setPhoneNumber(message.getText());
            apply.setIsFilePath(1);

        } else if (apply.getFilePath().equals("Register") && apply.getIsFilePath()==1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDCC1 Fayl: \n" +
                            "\n" +
                            "CV yuboring (pdf, docx, doc)")
                    .chatId(String.valueOf(chat_id))
                    .build());
            apply.setPhoneNumber(message.getText());
            apply.setFilePath(message.getText());

        } else if (message.getText().equals("Ha") && apply.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Ma'lumotlaringiz ish beruvchiga yuborildi");
            sm.setParseMode("HTML");
            sm.setChatId(String.valueOf(message.getChatId()));
            apply.setState(State.COMPLETED.toString());
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("Yo'q") && apply.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Qabul qilinmadi" +
                    "\n/start so`zini bosing. E'lon berish qaytadan boshlanadi");
            sm.setChatId(String.valueOf(message.getChatId()));
            apply.setState(State.DENIED.toString());
            apply.setIsJobId(0);
            apply.setIsName(0);
            apply.setIsAge(0);
            apply.setIsPhone(0);
            apply.setIsFilePath(0);
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);
        }
        applyService.update(apply);
    }

    @Override
    public void sendCV(Message message, Integer id) throws IOException {

        long chat_id = message.getChatId();
        Document document = message.getDocument();
        apply = applyService.findBy(chat_id, id);
        String fileId = document.getFileId();

        String urlContents = getUrlContents("https://api.telegram.org/bot" + token + "/getFile?file_id=" + fileId);
        apply.setFilePath(urlContents);
        apply.setToken(token);

        if (apply.getState().equals(State.NONE.toString()) && apply.getIsFilePath()==1) {
            markup = new ReplyKeyboardMarkup();
            keyboardRow = new ArrayList<>();
            messageSender.sendMessage(SendMessage.builder()
                    .text("\uD83C\uDF93 Id: " + apply.getJobId() + "\n" +
                            "\uD83D\uDC68\u200D\uD83D\uDCBC Xodim: " + apply.getName() + "\n" +
                            "\uD83D\uDD51 Yosh: " + apply.getAge() + "\n" +
                            "\uD83C\uDDFA\uD83C\uDDFF Telegram: @" + apply.getUsername() + "\n" +
                            "\uD83D\uDCDE Aloqa: " + apply.getPhoneNumber())
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
            apply.setState(State.CHECKED.toString());
            log.info("File was uploaded");
        }
        applyService.update(apply);
    }

    @Override
    public void help(Message message) {
        messageSender.sendMessage(SendMessage
                .builder().text("Bu bot bir narsa bir narsa")
                .chatId(String.valueOf(message.getChatId()))
                .build());
        log.info("info");
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

    public static void info(Message message, long user_id) {
        String username = message.getFrom().getUserName();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String format = dateFormat.format(date);

        apply.setUserId(user_id);
        apply.setUsername(username);
        apply.setState(State.NONE.toString());
        apply.setRegistrationTime(format);
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
}
