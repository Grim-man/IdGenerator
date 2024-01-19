create schema if not exists test;

use test;

create table if not exists sequence
(
    name       varchar(20),
    seq_no     int not null
);

insert into sequence(name, seq_no)
values ('test',0)