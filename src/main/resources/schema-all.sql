create table accounts
(
    accountid int auto_increment
        primary key,
    balance   decimal(10, 2) null
);

create table oprecord
(
    id         int auto_increment
        primary key,
    accountid  int                                       null,
    opmoney    decimal(10, 2)                            null,
    optime     datetime                                  null,
    optype     enum ('deposite', 'withdraw', 'transfer') null,
    transferid varchar(50)                               null
);