create table if not exists service_mail.mails (
    id bigserial not null primary key ,
    sentTo text not null ,
    subject text not null ,
    text text not null
);