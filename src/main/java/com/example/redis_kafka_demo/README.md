Просмотр всех продуктов (выдача без параметров ограничена 10 элементами):
Метод: GET
Адрес: http://localhost:8080/api/products/

Просмотр продукта по ID:
Метод: GET
Адрес: http://localhost:8080/api/products/{ID}
Пример: http://localhost:8080/api/products/2

Создание продукта:
Метод: POST
Адрес: http://localhost:8080/api/products/
BodyType: JSON
Пример:
{
"name": "Молоко козье",
"description": "Молоко козье, 1.5% жирности",
"price": 125.00,
"quantity": 75
}

Обновление продукта:
Метод: PUT
Адрес: http://localhost:8080/api/products/{ID}
BodyType: JSON
Пример: http://localhost:8080/api/products/2
{
"name": "Молоко козье",
"description": "Молоко козье, 3.2% жирности",
"price": 150.00,
"quantity": 100
}