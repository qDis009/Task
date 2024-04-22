create table bank.exchange_rate
(
    close_rate float(53) not null,
    id serial not null,
    previous_close_rate float(53) not null,
    created timestamp(6),
    currency_from varchar(255),
    currency_to varchar(255),
    primary key (id)
);
create table bank.limit
(
    id serial not null,
    spent_in_month float(53) not null,
    sum float(53) not null,
    user_id integer,
    created timestamp(6),
    currency_short_name varchar(255),
    expense_category varchar(255),
    primary key (id)
);
create table bank.transaction
(
    id serial not null,
    limit_exceeded boolean not null,
    limit_id integer,
    sum float(53) not null,
    user_id integer,
    account_from bigint not null,
    account_to bigint not null,
    completed timestamp(6),
    currency_short_name varchar(255),
    expense_category varchar(255),
    primary key (id)
);
create table bank.user
(
    id serial not null,
    full_name varchar(255),
    primary key (id)
);
alter table if exists bank.limit
    add constraint FK6y87h67i5m3brqvvpmgq2jq2t
    foreign key (user_id) references bank.user;
alter table if exists bank.transaction
    add constraint FKas7iu65cugx6vsvxrr7rc7u08
    foreign key (limit_id) references bank.limit;
alter table if exists bank.transaction
    add constraint FKsg7jp0aj6qipr50856wf6vbw1
    foreign key (user_id) references bank.user;
