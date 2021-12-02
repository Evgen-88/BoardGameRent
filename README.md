<h1>BoardGameRent</h1>

<h3>An app for the board games rental and accessories purchase.</h3>

1. **General information**: the client places an order by selecting the required board game(s) for rent<br/>
and/or accessories for purchase. The manager checks the order and refreshes its status.<br/>
Also a manager updates the database and expands the range of the offered board games and accessories.<br/>
2. **Roles**: customer, manager, admin. One user can have several roles.<br/>
3. **Client functions**: create/update order.<br/>
4. **Manager functions**: view the list of orders, update/delete order,<br/>
create/update/delete board games and accessories.<br/>
5. **Administrator functions**: view the list of roles, delete users,<br/>
add roles to user, remove roles from user.<br/>
6. **Common functions**: create new user, view the list of the users,<br/>
view the list of the board games, view the list of accessories.<br/>

**The following technologies were used**:
- Lombok
- Maven
- Flyway
- JDBC
- Hibernate
- Spring
- SpringCore
- SpringBoot
- SpringData
- SpringSecurity

For the main application and tests the **H2** database was used.

-----------------------------------------------------------------------------------------------------------

<h3>Приложение для аренды настольных игр и покупки сопутствующих товаров (аксессуаров).</h3>

1. **Общая информация**: клиент оформляет заказ, выбирая настольные игры для аренды<br/>
и/или аксессуары для покупки. Менеджер проверяет заказ и обновляет его статус.<br/>
Также менеджер обновляет базу данных путем пополнения ассортимента предлагаемых настольных игр и аксессуаров.<br/>
2. **Роли**: клиент (customer), менеджер (manager), администратор (admin).<br/>
У одного пользователя может быть несколько ролей.<br/>
3. **Функции клиента**: создавать, изменять заказ.<br/>
4. **Функции менеджера**: просматривать список заказов, изменять/удалять заказ,<br/>
добавлять/изменять/удалять настольные игры и аксессуары из/в базу данных.<br/>
5. **Функции администратора**: просматривать список ролей, удалять пользователей (user),<br/>
добавлять/удалять роли пользователя.<br/>
6. **Общие функции**: создавать новых пользователей, просматривать список пользователей,<br/>
просматривать список настольных игр, просматривать список аксессуаров.<br/>

**В приложении были использованы следующие технологии**:
- Lombok
- Maven
- Flyway
- JDBC
- Hibernate
- Spring
- SpringCore
- SpringBoot
- SpringData
- SpringSecurity

Для основного приложения и для тестов использовалась база данных **H2**.
