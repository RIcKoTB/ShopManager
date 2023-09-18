INSERT INTO category(name)
VALUES ('Мясо'),
       ('Молочні товари'),
       ('Напитки');

INSERT INTO role(name)
VALUES ('admin'),
       ('user');

INSERT INTO users(login, password, role_id)
VALUES ('admin', 132546, 1),
       (132546, 132546, 2);

INSERT INTO session(user_id, data)
VALUES (1 ,'2023-05-18 14:30:00'),
       (2 ,'2023-05-18 14:30:00');

INSERT INTO goods(name, photo, category_id, price)
VALUES ('Стейк рібай', 'photo', 1, 320.0),
       ('Молоко', 'photo', 2, 20.0);

INSERT INTO departments(city, street, photo)
VALUES ('Мукачево', 'Духновича', LOAD_FILE('/com/example/accountingofgoods/images/imagesDB/21.jpg')),
       ('Ужгород', 'Мукачівська', LOAD_FILE('./resources/com/example/accountingofgoods/images/imagesDB/21.jpg'));

INSERT INTO distributors(full_name, goods_id, company, logo)
VALUES ('Швецов Олег Вікторович', '1', 'BestStacke', LOAD_FILE('/resources/com/example/accountingofgoods/images/imagesDB/21.jpg'));

INSERT INTO basket(user_id, goods_list, data_create)
VALUES (1, '2,3,4', '2023-05-18'),
       (1, '3,2,4', '2023-05-18');

INSERT INTO store(name, good_id, count, department_id, distributor_id)
VALUES ('Long Bar', 2, 3, 2, 1),
       ('Chilli', 1, 5, 2, 1);

INSERT INTO history_buy(user_id, basket_id, data)
VALUES (2, 1, '2023-05-18'),
       (2, 2, '2023-05-18');

INSERT INTO history_distributor(distributor_id, department_id, data)
VALUES (1, 2, '2023-05-18'),
       (1, 1, '2023-05-18');


