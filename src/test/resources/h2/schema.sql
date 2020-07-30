DROP TABLE IF EXISTS RECOVERY_TASKS;

create table RECOVERY_TASKS
(
    ID NUMBER(11) constraint RECOVERY_TASKS_PKC primary key,
    TYPE VARCHAR2(30) not null,
    STATUS VARCHAR2(30) not null,
    HOGEID VARCHAR2(30),
    PARAMETER VARCHAR2(255),
    COUNT NUMBER(2) default 0 not null,
    CREATED_AT DATE default sysdate,
    UPDATED_AT DATE default sysdate,
    UPDATE_USER VARCHAR2(30) not null
);
