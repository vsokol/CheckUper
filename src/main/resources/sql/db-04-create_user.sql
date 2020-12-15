-- создание таблиц пользователей
create table if not exists users (
    id        bigserial
  , login     varchar(15) not null
  , password  varchar(50)
  , name      text
  , is_lock   boolean     not null default true
);

alter table if exists users add constraint users_id_pk primary key (id);

alter table if exists users add constraint users_login_uk unique (login);

alter sequence if exists users_id_seq restart with 10000;

comment on table users is 'Пользователи';
comment on column users.id       is 'Идентификатор';
comment on column users.login    is 'Логин';
comment on column users.password is 'Пароль';
comment on column users.name     is 'ФИО';
comment on column users.is_lock  is 'Блокировка';
