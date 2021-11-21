drop table if exists service_retention.retention;
create table service_retention.retentions (
    id serial NOT NULL PRIMARY KEY,
    student_id bigint not null,
    note text DEFAULT NULL,
    student_name text,
    reason text
);

insert into service_retention.retentions (note, reason, student_id, student_name)
values ('Temporary Leave', 'Em buon lam',2 ,'Nguyen Phan Hoang Tu');
insert into service_retention.retentions (note, reason, student_id, student_name)
values ('Permanently Leave', 'Cuoc song nay that kho khan', 3, 'Nguyen  Van A');

create sequence if not exists service_retention.seq_retention start 100 increment 1;