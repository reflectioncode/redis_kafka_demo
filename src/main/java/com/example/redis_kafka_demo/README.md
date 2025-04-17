1) Запуск приложения:
    1.1) Запустить все сервисы в docker-compose;
    1.2) Запустить класс RedisKafkaDemoApplication

2) Перечень API;
Просмотр всех продуктов (выдача без параметров ограничена 10 элементами):
    Method GET
    Adress: http://localhost:8080/api/products/
    Example: http://localhost:8080/api/products/?page=1&size=7

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
"name": "Молоко коровье",
"description": "Молоко козье, 1.5% жирности",
"price": 125.00,
"quantity": 75
}

Обновление продукта:
    Method: PUT
    Adress: http://localhost:8080/api/products/{ID}
    BodyType: JSON
    Example: http://localhost:8080/api/products/2
{
"name": "Молоко козье",
"description": "Молоко козье, 3.2% жирности",
"price": 150.00,
"quantity": 100
}