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

-- таблица задач
create table if not exists task (
    id          bigserial
  , name        varchar(50)
  , status      varchar(10)
  , dt_start    timestamp with time zone
  , dt_end      timestamp with time zone
  , dt_comleted timestamp with time zone
  , customer_id bigint
  , executor_id bigint
  , descr       text
);

alter table if exists task add constraint task_id_pk primary key (id);
alter table if exists task add constraint task_customer_id_fk foreign key (customer_id) references customer(id);
alter table if exists task add constraint task_executor_id_fk foreign key (executor_id) references executor(id);

create index if not exists task_customer_id_idx on task(customer_id);
create index if not exists task_executor_id_idx on task(executor_id);

alter sequence if exists task_id_seq restart with 10000;

comment on table task is 'Задачи';
comment on column task.id          is 'Идентификатор';
comment on column task.name        is 'Название';
comment on column task.status      is 'Статус (NEW, TODO, INPROGRESS, DONE';
comment on column task.dt_start    is 'Дата и время начала, когда можно начать выполнять задание';
comment on column task.dt_end      is 'Дата и время окончание, когда можно начать выполнять задание';
comment on column task.dt_comleted is 'Дата выполнения';
comment on column task.customer_id is 'Заказчик';
comment on column task.executor_id is 'Исполнитель';
comment on column task.descr       is 'Описание задачи';

-- таблица типов элементов чек-листов
create table if not exists checklist_item_type (
    id    bigserial
  , code  varchar(15) not null
  , name  varchar(50) not null
  , descr text
);

alter table if exists checklist_item_type add constraint chechlist_item_type_id_pk primary key (id);
alter table if exists checklist_item_type add constraint checklist_item_type_code_uk unique (code);

alter sequence if exists checklist_item_type_id_seq restart with 10000;

comment on table checklist_item_type is 'Задачи';
comment on column checklist_item_type.id    is 'Идентификатор';
comment on column checklist_item_type.code  is 'Код';
comment on column checklist_item_type.name  is 'Название';
comment on column checklist_item_type.descr is 'Описание';

-- таблица чек-листов
create table if not exists checklist (
    id      bigserial
  , task_id bigint      not null  
  , name    varchar(50) not null
  , descr   text
);

alter table if exists checklist add constraint checklist_id_pk primary key (id);
alter table if exists checklist add constraint checklist_id_fk foreign key (task_id) references task(id);

create index if not exists checklist_id_idx on checklist(task_id);

alter sequence if exists checklist_id_seq restart with 10000;

comment on table checklist is 'Чек-лист';
comment on column checklist.id      is 'Идентификатор';
comment on column checklist.task_id is '';
comment on column checklist.name    is 'Название';
comment on column checklist.descr   is 'Описание';

-- таблица элементов чек-листов
create table if not exists checklist_item (
    id           bigserial
  , type_id      bigint      not null
  , checklist_id bigint      not null  
  , info         varchar(50)
  , is_required  boolean     not null
  , descr        text
);

alter table if exists checklist_item add constraint checklist_item_id_pk primary key (id);
alter table if exists checklist_item add constraint checklist_item_type_id_fk foreign key (type_id) references checklist_item_type(id);
alter table if exists checklist_item add constraint checklist_item_checklist_id_fk foreign key (checklist_id) references checklist(id);

create index if not exists checklist_item_type_id_idx on checklist_item(type_id);
create index if not exists checklist_item_checklist_id_idx on checklist_item(checklist_id);

alter sequence if exists checklist_item_id_seq restart with 10000;

comment on table checklist_item is 'Элемент чек-листа';
comment on column checklist_item.id           is 'Идентификатор';
comment on column checklist_item.type_id      is 'Тип элемента (пока только один тип - LABEL)';
comment on column checklist_item.checklist_id is 'Чек-лис, в который входит элемент';
comment on column checklist_item.info         is 'Краткое описание элемента (что будет выведено на экран исполнителю)';
comment on column checklist_item.is_required  is 'Обязательность выполнения текущего элемента';
comment on column checklist_item.descr        is 'Комментарии к элементу';

-- таблица результатов выполнения задач в разрезе элементов
create table if not exists result_item (
    id                bigserial
  , checklist_item_id bigint    not null
  , is_completed      boolean   not null default false
);

alter table if exists result_item add constraint result_item_id_pk primary key (id);
alter table if exists result_item add constraint result_item_checklist_item_id_fk foreign key (checklist_item_id) references checklist_item(id);

create index if not exists result_item_id_idx on result_item(checklist_item_id);

alter sequence if exists result_item_id_seq restart with 10000;

comment on table result_item is 'Результаты выполнения';
comment on column result_item.id                is 'Идентификатор';
comment on column result_item.checklist_item_id is 'Элемент чек-листе';
comment on column result_item.is_completed      is 'Признак выполнения (true - выполнен, иначе - не выполнен)';