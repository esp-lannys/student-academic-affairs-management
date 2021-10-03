create table if not exists service_mail.mail (
    id bigserial not null primary key ,
    "to" text not null ,
    subject text not null ,
    text text not null
)