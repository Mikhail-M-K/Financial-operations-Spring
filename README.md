# Financial-operations-Spring
REST API сервис для управления данными о городах и достопримечательностях
 - для запуска необходимо:
   - в файле application.yml указать конфигурацию используемой базы данных, например:
      - url: jdbc:postgresql://localhost:5432/finpro
      - username: admin
      - password: admin
   - запустить FinsOperationsApplication
      - Путь к .java: src/main/java/com/example/finoper/FinsOperationsApplication.java

- Управление клиентами:
   - Получение информации о клиенте:
    - метод GET
    - URL "/client/{id}"
      где id идентификатор клиента в бд
  - Получение информации обо всех клиентах:
    - метод GET
    - URL "/clients"
  - Добавление клиента:
    - метод POST
    - URL "/client"
    - тело запроса: объект типа ClientCreateDto в JSON формате

- Управление счетами клиентов:
  - Получение информации о счетах клиента:
    - метод GET
    - URL "/client/accounts/{id}"
      где id идентификатор клиента в бд
  - Добавление счета для клиента:
    - метод POST
    - URL "/clientaccount"
    - тело запроса: объект типа ClientAccountCreateDto в JSON формате
    
- Управление кассовыми ордерами по счетам клиента:
  - Получение информации о кассовых ордерах по счету клиента:
    - метод GET
    - URL "/client/accounts/cashorders/{id}"
      где id идентификатор счета клиента в бд
  - Добавление счета для клиента:
    - метод POST
    - URL "/clientaccount"
    - тело запроса: объект типа CashOrderRequestDto в JSON формате
    
- Управление транзакциями по счетам клиента:
  - Получение информации о транзакциях по счету клиента:
    - метод GET
    - URL "/client/accounts/transactions/{id}"
      где id идентификатор счета клиента в бд
  - Добавление транззакции между счетами одного пользователя:
    - метод POST
    - URL "/transfer/oneuser"
    - тело запроса: объект типа TransactionalTransferRequestDto в JSON формате
  - Добавление транзакции между счетами разных пользователей:
    - метод POST
    - URL "/transfer"
    - тело запроса: объект типа TransactionalTransferRequestDto в JSON формате
