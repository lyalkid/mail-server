# Mail Server Application

Простое консольное приложение для имитации работы почтового сервера с фильтрацией спама.

## Основные возможности

- ✅ Регистрация пользователей
- 📨 Отправка сообщений между пользователями
- 🛡️ Система спам-фильтров:
    - Простые фильтры (по ключевому слову "spam")
    - Фильтры по ключевым словам
    - Фильтрация повторяющихся слов
    - Блокировка отправителей
- 📥/📤 Управление входящими/исходящими сообщениями
- 🔍 Просмотр спам-сообщений

## Доступные команды
add      - Добавить нового пользователя
list     - Показать всех пользователей
send     - Отправить сообщение
inbox    - Просмотреть входящие
spam     - Просмотреть спам
outbox   - Просмотреть исходящие
setfilter - Настроить фильтры
exit     - Выйти

## Примеры использования
### Добавление пользователя:

```
> add
Enter user name: Alice
User 'Alice' added
```

```
> setfilter
Enter user name: Bob
Available filter types:
1. simple - messages containing 'spam'
2. keywords - messages with specific keywords
3. repetition - messages with repeated words
4. sender - messages from specific sender
Enter 'done' to finish, 'cancel' to abort

Select filter type: keywords
Enter keywords (space-separated): urgent important
Added keywords filter: [urgent, important]
Successfully set 1 filters
```