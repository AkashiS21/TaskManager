Приложение Task Manager
Добро пожаловать в приложение Task Manager!Этот проект представляет собой простую api - систему управления задачами

Приложение Task Manager разработано на Java с использованием Spring Boot. Оно предоставляет функциональность для регистрации пользователей, аутентификации, управления задачами и комментариями. Для безопасной аутентификации и авторизации используется JWT.
Сборка и запуск


Клонируйте репозиторий
git clone [https://github.com/your-repository/task-manager.git](https://github.com/AkashiS21/TaskManager)
cd task-manager

Соберите проект
С Maven:
mvn clean install

С Gradle:
gradle build

Запуск: java -jar build/libs/TaskManager-0.0.1-SNAPSHOT.jar

Регистрация
POST /api/register
Тело запроса:
{
  "username": "string",
  "password": "string",
  "email": "string"
}


Логин
POST /api/register/authenticate
{
  "username": "string",
  "password": "string"
}


Задачи:
Получить все задачи для текущего пользователя
GET /api/task
Ответ:

  {
    "id": 1,
    "title": "string",
    "description": "string",
    "status": "string",
    "authorEmail": "string",
    "assigneeEmail": "string"
  }
]
Создать новую задачу
POST /api/task/createNewTask
Тело запроса:
{
  "title": "string",
  "description": "string",
  "status": "string",
  "assigneeEmail": "string"
}
Ответ:
{
  "id": 1,
  "title": "string",
  "description": "string",
  "status": "string",
  "authorEmail": "string",
  "assigneeEmail": "string"
}


Обновить задачу
PUT /api/task/updateTask/{id}
Тело запроса:
{
  "title": "string",
  "description": "string",
  "status": "string",
  "assigneeEmail": "string"
}
Ответ:
{
  "id": 1,
  "title": "string",
  "description": "string",
  "status": "string",
  "authorEmail": "string",
  "assigneeEmail": "string"
}


Удалить задачу
DELETE /api/task/delete/{id}
Ответ:
HTTP 204 No Content
Обновить статус задачи
PATCH /api/task/{id}/updateStatus
Параметры запроса:
{
  "taskStatus": "string"
}
Ответ:
{
  "id": 1,
  "title": "string",
  "description": "string",
  "status": "string",
  "authorEmail": "string",
  "assigneeEmail": "string"
}


Получить задачи по автору
GET /api/task/tasksByAuthor/{authorEmail}
Ответ:
[
  {
    "id": 1,
    "title": "string",
    "description": "string",
    "status": "string",
    "authorEmail": "string",
    "assigneeEmail": "string"
  }
]


Получить задачи по исполнителю
GET /api/task/taskByAssegnee/{assegneeEmail}
Ответ:
[
  {
    "id": 1,
    "title": "string",
    "description": "string",
    "status": "string",
    "authorEmail": "string",
    "assigneeEmail": "string"
  }
]


Комментарии
Получить комментарии для задачи
GET /api/task/comments/{taskId}
Ответ:
[
  {
    "id": 1,
    "content": "string",
    "authorEmail": "string"
  }
]
Создать новый комментарий
POST /api/task/comments/createComment/{taskId}
Тело запроса:
{
  "content": "string"
}
Ответ:
{
  "id": 1,
  "content": "string",
  "authorEmail": "string"
}



Настройка Docker
Чтобы запустить приложение в Docker-контейнере:
Создайте Docker-образ
docker build -t task-manager .
Запустите Docker-контейнер
docker run -p 8080:8080 task-manager
Приложение будет доступно по адресу http://localhost:8080.
