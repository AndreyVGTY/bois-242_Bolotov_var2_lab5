import config.BotConfig;
import processor.TextProcessor;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class TextProcessorBot implements LongPollingSingleThreadUpdateConsumer {

    private final TelegramClient telegramClient;

    public TextProcessorBot() {
        telegramClient = new OkHttpTelegramClient(BotConfig.BOT_TOKEN);
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            String answer;
            if (userText.equals("/start")) {
                answer = "Привет! Я инвертирую любой текст.\nПример: Telegram → margeleT";
            } else if (userText.equals("/help")) {
                answer = "Отправь мне любой текст, и я верну его задом наперёд.";
            } else {
                answer = TextProcessor.process(userText);
            }

            sendText(chatId, answer);
        }
    }

    private void sendText(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            System.err.println("❌ Ошибка отправки: " + e.getMessage());
        }
    }
}