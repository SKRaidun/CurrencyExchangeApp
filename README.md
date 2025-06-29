# Currency Exchange Application

## Описание

Приложение для конвертации валют, реализующее REST API для получения текущих курсов валют и выполнения конвертаций. Проект был разработан в рамках обучающего курса по Java Backend разработке [currency-exchange](https://zhukovsd.github.io/java-backend-learning-course/projects/currency-exchange/).

## Стек

![Java](https://img.shields.io/badge/java-black.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Jakarta EE](https://img.shields.io/badge/jakarta_ee-black?style=for-the-badge&labelColor=white)
![Static Badge](https://img.shields.io/badge/mvc(s)-black?style=for-the-badge&labelColor=white)
![SQLite](https://img.shields.io/badge/sqlite-black.svg?style=for-the-badge&logo=sqlite&logoColor=white)
![JDBC](https://img.shields.io/badge/jdbc-black?style=for-the-badge&labelColor=white)
![HTTP](https://img.shields.io/badge/http-black?style=for-the-badge&labelColor=white)
![Rest Api](https://img.shields.io/badge/REST%20API-black?style=for-the-badge&labelColor=white)
![JSON](https://img.shields.io/badge/json-black?style=for-the-badge&labelColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-black?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Postman](https://img.shields.io/badge/postman-black?style=for-the-badge&logo=postman&logoColor=white)


## Основные возможности

- Получение списка всех доступных валют
- Получение информации о конкретной валюте по коду
- Расчет суммы конвертации между валютами
- Добавление новых валют в систему


## API Endpoints

### Валюты

- `GET /currencies` - получить список всех валют
- `GET /currency/{code}` - получить информацию о валюте по коду (например, RUB)
- `POST /currencies` - добавить новую валюту

### Конвертация

- `GET /exchangeRates` - получить все курсы обмена
- `GET /exchangeRate/{pair}` - получить курс обмена для пары валют (например, RUBUSD)
- `POST /exchangeRates` - добавить новый курс обмена
- `PATCH /exchangeRate/{pair}` - обновление существующего в базе обменного курса.
- `GET /exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT` - расчёт перевода определённого количества средств из одной валюты в другую.

## Запуск приложения

1. Клонируйте репозиторий:
   ```
   git clone https://github.com/SKRaidun/CurrencyExchangeApp.git
   ```
2. Перейдите в директорию проекта:
   ```
   cd CurrencyExchangeApp
   ```
3. Соберите проект с помощью Maven:
   ```
   mvn clean install
   ```
4. Полученный war артефакт (target/CurrencyExchangeApp.war) скопируйте в директорию развертывания вашего сервера приложений (использовался Apache Tomcat 9 версии):
   ```
   Tomcat 9.0.*: $CATALINA_HOME/webapps/
   ```
   
Приложение будет доступно по адресу: `http://localhost:8080/CurrencyExchangeApp`

## Примеры запросов

Получение информации о валюте:
```
GET /currency/RUB
```

Конвертация суммы:
```
GET /exchange?from=USD&to=RUB&amount=100
```
