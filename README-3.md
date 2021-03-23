
* https://coderoad.ru/23214454/org-hibernate-MappingException-неизвестная-сущность-annotations-Users
* https://overcoder.net/q/1288149/транзакция-не-была-успешно-запущена-в-то-время-как-txcommit-окружен-условием-if

```text
Hibernate: 
    create table tbl_developers (
        id integer not null,
        experience integer,
        FIRST_NAME varchar(255),
        LAST_NAME varchar(255),
        salary integer,
        specialty varchar(255),
        primary key (id)
    )
```

```text
Hibernate: 
    insert 
    into
        tbl_developers
        (experience, FIRST_NAME, LAST_NAME, salary, specialty, id) 
    values
        (?, ?, ?, ?, ?, ?)
Hibernate: 
    insert 
    into
        tbl_developers
        (experience, FIRST_NAME, LAST_NAME, salary, specialty, id) 
    values
        (?, ?, ?, ?, ?, ?)
Hibernate: 
    call next value for SEQ_TBL_DEVELOPER
```