1) Запуск приложения:
    1.1) Запустить все сервисы в docker-compose;
    1.2) Запустить класс RedisKafkaDemoApplication, если топика нет, автоматически создастся топик с 3 партициями. 

2) Перечень API;
Просмотр всех продуктов (выдача без параметров ограничена 10 элементами):
    Method GET
    Adress: http://localhost:8080/api/products/
    Example: http://localhost:8080/api/products/?page=2&size=7

Просмотр продукта по ID:
    Method: GET
    Adress: http://localhost:8080/api/products/{ID}
    Example: http://localhost:8080/api/products/2

Создание продукта:
    Method: POST
    Adress: http://localhost:8080/api/products/
    BodyType: JSON
    Example:
{
"name": "Product 1",
"description": "Description for product 1",
"price": 125.00,
"quantity": 75
}

Обновление продукта:
    Method: PUT
    Adress: http://localhost:8080/api/products/{ID}
    BodyType: JSON
    Example: http://localhost:8080/api/products/2
{
"name": "Product 1",
"description": "Description for product 1",
"price": 125.00,
"quantity": 75
}

Создание, обновление и удаление отправляется в кафку. 
Оттуда же сообщение забирается и логируется информация о получении. Просмотреть по адресу:
http://localhost:8080/api/events

При первом обращение GET продукт добавляется в Redis, при DELETE продукт удаляется из Redis и из БД.
В данной ветке все операции выполняются в рамках одного микросервиса.