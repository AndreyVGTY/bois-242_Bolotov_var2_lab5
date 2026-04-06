import config.BotConfig;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

public class TextBotApplication {
    public static void main(String[] args) {
        try {
            TextProcessorBot bot = new TextProcessorBot();

            TelegramBotsLongPollingApplication app = new TelegramBotsLongPollingApplication();
            app.registerBot(BotConfig.BOT_TOKEN, bot);

            System.out.println("✅ Бот успешно запущен!");
            System.out.println("🤖 @" + BotConfig.BOT_USERNAME);
            System.out.println("Для остановки нажмите Ctrl+C");

            Thread.currentThread().join();

        } catch (Exception e) {
            System.err.println("❌ Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}