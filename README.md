# Тестовое задание в sberclass.ru

## Задача

Расширение функционала районной библиотеки 50 тыс экземпляров
Наступила пандемия, нужно минимизировать поход читателей в библиотеку
Есть легаси, нужно сделать микросервис на Java
каталог книг, 3 эндпоинта

Дана сущность book

id number
name string
author string
isbn string

/getBooks возвращает List<Book>
Все книги, все атрибуты в формате JSON

/getStatistic возвращает HashMap<Char, Integer>
статистика
Ключ - значение
Разбиение книг по категории, с какой буквы начинается книга

/getBooksByLetter возвращает List<Book>
Все книги, все атрибуты в формате JSON
+ фильтр: все книги начинается с буквы переданной в параметре

Слои:
Entity+dto
Repository
Service
Controller
