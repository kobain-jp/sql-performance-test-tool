alter session set container=ORCLPDB1;
create user developer identified by developer;
grant create session to developer;
grant create table to developer;
grant create sequence to developer;
alter user developer quota unlimited on USERS;
grant select on SYS.V_$SYSTEM_PARAMETER TO developer;
grant select on SYS.V_$SQLSTATS TO developer;
grant select on SYS.V_$SYSTEM_PARAMETER TO developer;

conn developer/developer@ORCLPDB1;

CREATE TABLE book(book_id NUMBER GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY, isbn NUMBER, title NVARCHAR2(50), author NVARCHAR2(50), release_date DATE);
INSERT INTO book(title, isbn,  author, release_date) VALUES ('SLAM DUNK 1',9784088716114,'Takehiko Inoue',TO_DATE('1991/02/08','yyyy/mm/dd'));
INSERT INTO book(title, isbn,  author, release_date) VALUES ('SLAM DUNK 2',9784088716121,'Takehiko Inoue',TO_DATE('1991/06/10','yyyy/mm/dd'));
INSERT INTO book(title, isbn,  author, release_date) VALUES ('REAL 1',9784088761435,'Takehiko Inoue',TO_DATE('2001/03/19','yyyy/mm/dd'));

exit;