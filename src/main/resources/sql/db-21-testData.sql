insert into customer (id, name, address, descr) values (1001, 'Петров Иван', 'Москва', 'Описание заказчика');
insert into customer (id, name, address, descr) values (1002, 'Скорлупкин Петр', null, null);
insert into customer (id, name, address, descr) values (1003, 'Стрельцов Никанор', 'Марс 12', null);

insert into executor (id, name, available, descr) values (1001, 'Крылов Степа', true, null);
insert into executor (id, name, available, descr) values (1002, 'Ступин Владлен', true, 'Ступин Владлен Владленович');
insert into executor (id, name, available, descr) values (1003, 'Ярополк Святославич', false, 'князь киевский');

insert into request (id, name, status, dt_start, dt_end, dt_comleted, customer_id, executor_id, descr)
values (1001, 'Помочь по дому', 'TODO', TIMESTAMP WITH TIME ZONE '2021-02-10 10:00:00+03', TIMESTAMP WITH TIME ZONE '2021-02-10 19:00:00+03', null, 1001, 1002, 'Помочь по дому');
insert into request (id, name, status, dt_start, dt_end, dt_comleted, customer_id, executor_id, descr)
values (1002, 'Помочь по хозяйству', 'NEW', TIMESTAMP WITH TIME ZONE '2021-02-10 10:00:00+03', TIMESTAMP WITH TIME ZONE '2021-02-10 13:30:00+03', null, null, null, 'Помочь по хозяйству');
insert into request (id, name, status, dt_start, dt_end, dt_comleted, customer_id, executor_id, descr)
values (1003, 'Покупать хлеб', 'INPROGRESS', TIMESTAMP WITH TIME ZONE '2020-12-15 10:00:00+03', TIMESTAMP WITH TIME ZONE '2020-12-10 19:00:00+03', null, 1003, 1002, 'Покупать каждый день булку хлеба и пакет молока');
insert into request (id, name, status, dt_start, dt_end, dt_comleted, customer_id, executor_id, descr)
values (1004, 'Вынести мусор', 'DONE', TIMESTAMP WITH TIME ZONE '2020-12-10 10:00:00+03', TIMESTAMP WITH TIME ZONE '2020-12-15 19:00:00+03', TIMESTAMP WITH TIME ZONE '2020-12-13 13:48:24+03', 1002, 1002, 'Вынести мусор и старый шкаф');

commit;