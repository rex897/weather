import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException, RuntimeException, LifecycleException, ServletException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addWebapp("", new File("/").getAbsolutePath());
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources,
                "/",
                new File("").getAbsolutePath(),
                "/"));
        resources.addPreResources(new DirResourceSet(
                resources,
                "/",
                new File("").getAbsolutePath(),
                "/"));
        context.setResources(resources);

        tomcat.start();
        int updateId = 0;
        String apikey = System.getenv("token");
        TelegramBot bot = TelegramBotAdapter.build(apikey);
        while (true) {
            try {
                GetUpdates getUpdates = new GetUpdates().limit(1).offset(updateId).timeout(50);
                GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
                List<Update> updates = updatesResponse.updates();
                if (updates.isEmpty()) {
                    continue;
                }
                Update update = updates.get(0);
                Message message = update.message();
                if (message != null) {
                    String text = message.text();
                    if (text == null) {
                        sendMessage(bot, "Я не знаю, что ответить, попробуйте команду /start", message.chat().id());
                    }
                    if (text != null) {
                        System.out.println(message.chat().firstName() + ":" + " " + message.text());
                        if (text.equals("/start")) {
                            sendMessage(bot, "Привет, " + message.chat().firstName()+ ", я бот, который будет присылать тебе погоду по заданному тобой городу. " +
                                    "Попробуй ввести команду /weather и название города через проблел </weather город> ", message.chat().id());
                        } else if (text.contains("/weather")) {
                            if (text.length() > 9) {
                                String gorod = text.substring(9);
                                Weather weather = new Weather();
                                weather.getWeather(gorod);
                                sendMessage(bot, weather.getWeather(gorod), message.chat().id());
                            } else
                                sendMessage(bot, "Введите название города после команды '/weather'", message.chat().id());
                        } else {
                            sendMessage(bot, "Неизвестная команда, попробуйте /start", message.chat().id());
                        }
                    }
                }
                updateId = update.updateId() + 1;
            } catch (RuntimeException t) {
                t.printStackTrace();
            }
        }
    }

    private static void sendMessage(TelegramBot bot, String text, long chatID) throws IOException {
        SendMessage request = new SendMessage(chatID, text);
        SendResponse sendResponse = bot.execute(request);
        Message response = sendResponse.message();
    }
}


