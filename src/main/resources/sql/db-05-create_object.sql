-- создание необходимых объектов
-- таблица исполнителей (волонтеров)
create table if not exists executor (
    id        bigserial
  , name      varchar(50) not null
  , available boolean     not null default false
  , descr     text
);
alter table if exists executor add constraint executor_id_pk primary key (id);

alter sequence if exists executor_id_seq restart with 10000;

comment on table executor is 'Исполнители';
comment on column executor.id        is 'Идентификатор';
comment on column executor.name      is 'ФИО';
comment on column executor.available is 'Доступность';
comment on column executor.descr     is 'Описание, например, что может делать';

-- таблица заказчиков
create table if not exists customer (
    id      bigserial
  , name    varchar(50) not null
  , address text
  , descr   text
);
alter table if exists customer add constraint customer_id_pk primary key (id);

alter sequence if exists customer_id_seq restart with 10000;

comment on table customer is 'Заказчики';
comment on column customer.id      is 'Идентификатор';
comment on column customer.name    is 'ФИО';
comment on column customer.address is 'Адрес';
comment on column customer.descr   is 'Описание заказчика';

-- таблица заявок
create table if not exists request (
    id          bigserial
  , name        varchar(50)
  , status      varchar(12)
  , dt_start    timestamp with time zone
  , dt_end      timestamp with time zone
  , dt_comleted timestamp with time zone
  , customer_id bigint
  , executor_id bigint
  , descr       text
);

alter table if exists request add constraint request_id_pk primary key (id);
alter table if exists request add constraint request_customer_id_fk foreign key (customer_id) references customer(id);
alter table if exists request add constraint request_executor_id_fk foreign key (executor_id) references executor(id);

create index if not exists request_customer_id_idx on request(customer_id);
create index if not exists request_executor_id_idx on request(executor_id);

alter sequence if exists request_id_seq restart with 10000;

comment on table request is 'Заявки';
comment on column request.id          is 'Идентификатор';
comment on column request.name        is 'Название';
comment on column request.status      is 'Статус (NEW, TODO, INPROGRESS, DONE';
comment on column request.dt_start    is 'Дата и время начала, когда можно начать выполнять заявку';
comment on column request.dt_end      is 'Дата и время окончание, когда можно начать выполнять заявку';
comment on column request.dt_comleted is 'Дата выполнения';
comment on column request.customer_id is 'Заказчик';
comment on column request.executor_id is 'Исполнитель';
comment on column request.descr       is 'Описание задачи';

-- таблица типов элементов чек-листов
create table if not exists task_type (
    id    bigserial
  , code  varchar(15) not null
  , name  varchar(50) not null
  , descr text
);

alter table if exists task_type add constraint task_type_id_pk primary key (id);
alter table if exists task_type add constraint task_type_code_uk unique (code);

alter sequence if exists task_type_id_seq restart with 10000;

comment on table task_type is 'Типы задач';
comment on column task_type.id    is 'Идентификатор';
comment on column task_type.code  is 'Код';
comment on column task_type.name  is 'Название';
comment on column task_type.descr is 'Описание';

-- таблица чек-листов
create table if not exists checklist (
    id         bigserial
  , request_id bigint      not null  
  , name       varchar(50) not null
  , descr      text
);

alter table if exists checklist add constraint checklist_id_pk primary key (id);
alter table if exists checklist add constraint checklist_id_fk foreign key (request_id) references request(id);

create index if not exists checklist_id_idx on checklist(request_id);

alter sequence if exists checklist_id_seq restart with 10000;

comment on table checklist is 'Чек-лист';
comment on column checklist.id         is 'Идентификатор';
comment on column checklist.request_id is '';
comment on column checklist.name       is 'Название';
comment on column checklist.descr      is 'Описание';

-- таблица задач для чек-листов
create table if not exists task (
    id           bigserial
  , type_id      bigint      not null
  , checklist_id bigint      not null  
  , info         varchar(50)
  , is_required  boolean     not null
  , descr        text
);

alter table if exists task add constraint task_id_pk primary key (id);
alter table if exists task add constraint task_type_id_fk foreign key (type_id) references task_type(id);
alter table if exists task add constraint task_checklist_id_fk foreign key (checklist_id) references checklist(id);

create index if not exists task_type_id_idx on task(type_id);
create index if not exists task_checklist_id_idx on task(checklist_id);

alter sequence if exists task_id_seq restart with 10000;

comment on table task is 'Задача по чек-листу';
comment on column task.id           is 'Идентификатор';
comment on column task.type_id      is 'Тип задачи (пока только один тип - LABEL)';
comment on column task.checklist_id is 'Чек-лис, в который входит задача';
comment on column task.info         is 'Краткое описание задачи (что будет выведено на экран исполнителю)';
comment on column task.is_required  is 'Обязательность выполнения задачи';
comment on column task.descr        is 'Комментарии к задачи';

-- таблица результатов выполнения задач в разрезе элементов
create table if not exists task_result (
    id                bigserial
  , task_id bigint    not null
  , is_completed      boolean   not null default false
);

alter table if exists task_result add constraint task_result_id_pk primary key (id);
alter table if exists task_result add constraint task_result_task_id_fk foreign key (task_id) references task(id);

create index if not exists task_result_id_idx on task_result(task_id);

alter sequence if exists task_result_id_seq restart with 10000;

comment on table task_result is 'Результаты выполнения';
comment on column task_result.id           is 'Идентификатор';
comment on column task_result.task_id      is 'Элемент чек-листе';
comment on column task_result.is_completed is 'Признак выполнения (true - выполнен, иначе - не выполнен)';
