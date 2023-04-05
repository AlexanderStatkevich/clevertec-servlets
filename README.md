# Receipt project

## Cache task

### Использованный стэк

* Java 17
* Gradle 7.5
* JUnit 5
* Java Servlet API 3.0.1
* Jackson Databind 2.14.1
* PostgreSQL 15.1

### Инструкция по запуску базовой версии

Необходимо собрать проект в .jar файл следующей командой, находясь в директории архива:

 ```sh
  gradle build
  ```

Далее запустить его, указав необходимые id товаров и их количество, а также номер дисконтной карты при необходимости:

  ```sh
java -cp jar-file-name main-class-name [аргументы] (например 1-2 2-3 3-4 card-1111) 
  ```

Также данные построчно можно передать в файле:

 ```sh
1-2
2-3
3-4
card-1111
  ```

для этого необходимо создать файл с исходными данными и при запуске в аргументы передать название файла в формате:

 ```sh
java -cp jar-file-name main-class-name [file-filename.txt]
  ```

Для выбора печати в консоль или в файл необходимо в поле PRINT_TYPE класса demo/Runner внести "file" или "console" (по
умолчанию печать в консоль).

### Инструкция по запуску расширенной версии с дополнительными пунктами

Основная расширенная верия проекта развернута в Docker. Образ проекта можно найти в
репозитории https://hub.docker.com/repository/docker/bumblebear/receipttask.
Для запуска образа из репозитория необходим файл docker-compose.yaml из прокта. DDL операции работы с базой данных
хранятся в корневом каталоге проетка, в файле init.sql, и используются в том числе для инициализации базы данных при
развертывании проетка с Docker образа, поэтому данный файл также необходим. Развернуть проект можно следующей командой
находясь в директории проекта:

 ```sh
docker compose up
  ```

При развертывании проекта передать исходные данные можно по эндпоинту /order в формате:

 ```sh
 http://host:port/WarFileName/order?[аргументы] (например 1=2&2=3&3=4&card=1234).
  ```

### Описание эндпойнтов с CRUD операциями по сущностям CommonProduct, DiscountCard:

* /products

POST

 ```sh
<ProductDto>
	<id>2</id>
	<name>Bread</name>
	<price>3.0</price>
	<saleTypes>
		<saleTypes>TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS</saleTypes>
	</saleTypes>
</ProductDto>
  ```

GET

 ```sh
 http://host:port/WarFileName/products?id={id}
  ```

PUT

 ```sh
<ProductDto>
	<id>2</id>
	<name>Bread</name>
	<price>3.0</price>
	<saleTypes>
		<saleTypes>TEN_PERCENT_OFF_FOR_MORE_THAN_FIVE_PRODUCTS</saleTypes>
	</saleTypes>
</ProductDto>
  ```

DELETE

 ```sh
 http://host:port/WarFileName/cards?id={id}
  ```

* /cards

POST

 ```sh
{"cardNumber":"1111","discount":0.03}
  ```

GET

 ```sh
 http://host:port/WarFileName/cards?number={number}
  ```

PUT

 ```sh
{"cardNumber":"1111","discount":0.03}
  ```

DELETE

 ```sh
 http://host:port/WarFileName/products?number={number}
  ```

## Контакты

Alexander Statkevich - aastatkevich@gmail.com
Project Link: [https://github.com/AlexanderStatkevich/ReceiptTask](https://github.com/AlexanderStatkevich/ReceiptTask)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
