# Лабораторная работа №5 — Telegram-бот

## Цель работы

Познакомиться с архитектурой взаимодействия клиент-сервер в контексте Telegram-ботов. Освоить базовые принципы работы с библиотекой Telegram Bot API, обработки входящих сообщений и отправки ответов.

---

## Вариант 2 — Инвертор строки

Бот принимает текстовое сообщение и возвращает его задом наперёд.

**Пример:**
```
Вход:  Telegram
Выход: margeleT

Вход:  Привет
Выход: тевирП
```

---

## Структура проекта

```
bois-242-Bolotov_var2_lab5/
└── src/
    ├── config/
    │   └── BotConfig.java       # Конфигурация бота (токен, имя)
    ├── processor/
    │   └── TextProcessor.java   # Логика инвертирования строки
    ├── TextProcessorBot.java    # Основной класс бота (Long Polling)
    └── TextBotApplication.java  # Точка входа, запуск бота
```

---

## Описание классов

### `config/BotConfig`
Хранит константы конфигурации бота. Токен загружается из переменной окружения `BOT_TOKEN` — никогда не указывай его прямо в коде.

### `processor/TextProcessor`
Содержит метод `process(String input)` — реализация варианта 2: инвертирование строки через `StringBuilder.reverse()`.

### `TextProcessorBot`
Реализует `LongPollingSingleThreadUpdateConsumer`. Получает обновления от Telegram, извлекает текст сообщения, вызывает `TextProcessor.process()` и отправляет ответ.

Обрабатывает команды:
- `/start` — приветственное сообщение
- `/help` — описание работы бота
- любой текст — инвертированная строка

### `TextBotApplication`
Главный класс с методом `main`. Регистрирует бота через `TelegramBotsLongPollingApplication` и удерживает главный поток через `Thread.currentThread().join()`.

---

## Принцип работы (Long Polling)

```
Бот → "Есть новые сообщения?" → Сервер Telegram
Сервер → возвращает список Update
Бот → обрабатывает каждый Update → отправляет ответ
Повторяется бесконечно...
```

---

## Зависимости (`pom.xml`)

```xml
<dependencies>
    <dependency>
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots-longpolling</artifactId>
        <version>9.2.1</version>
    </dependency>
    <dependency>
        <groupId>org.telegram</groupId>
        <artifactId>telegrambots-client</artifactId>
        <version>9.2.1</version>
    </dependency>
</dependencies>
```

---

## Запуск

### 1. Получить токен
- Открыть Telegram → найти **@BotFather**
- Отправить `/newbot` и следовать инструкциям
- Скопировать полученный токен

### 2. Задать токен через переменную окружения
В Run/Debug Configurations → Environment variables:
```
BOT_TOKEN=1234567890:xxxxxxxxxxxxxxxxxxxxxx
```

### 3. Запустить
Запустить `TextBotApplication` — в консоли появится:
```
✅ Бот успешно запущен!
🤖 @имя_бота
Для остановки нажмите Ctrl+C
```

---

## Тестирование

| Ввод | Ожидаемый ответ |
|---|---|
| `/start` | Приветственное сообщение |
| `/help` | Описание работы |
| `Telegram` | `margeleT` |
| `Привет` | `тевирП` |
| `12345` | `54321` |
| `Hello World` | `dlroW olleH` |

---

## Важно

> ⚠️ Никогда не публикуй токен в коде, README или на GitHub. Используй переменные окружения или `.env` файл добавленный в `.gitignore`.
