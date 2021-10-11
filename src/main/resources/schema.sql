CREATE TABLE IF NOT EXISTS teacherGrpc
(
    teacherId   long            primary key,
    name        varchar(100)    not null,
    age         int             not null,
    mail        varchar(100)    not null,
    subject    varchar(100)     not null
)