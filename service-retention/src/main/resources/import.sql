drop table if exists service_retention.retention;
create table service_retention.retentions (
    id serial NOT NULL,
    student_id bigint not null,
    note text DEFAULT NULL,
    student_email varchar(255) not null,
    student_name varchar(255) not null,
    reason varchar(255) not null,
    primary key (id)
);

create sequence if not exists service_retention.seq_retention start 100 increment 1;