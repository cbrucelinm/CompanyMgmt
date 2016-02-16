create database companymgmt1;

create user companymgmtuser1;
grant all on companymgmt1.* to 'companymgmtuser1'@'localhost' identified by 'companymgmtpsw1';

use companymgmt1;


drop table beneficial_owner;
drop table company;
drop table owner;

CREATE SEQUENCE owner_id_seq;
CREATE TABLE owner
(
id INT NOT NULL DEFAULT nextval('owner_id_seq'),
name varchar(255) NOT NULL,
address varchar(255) ,
city varchar(255) ,
country varchar(255) ,
email varchar(255) ,
phone varchar(255) ,
dob timestamp ,
ctime timestamp NOT NULL,
utime timestamp NOT NULL,
unique (id)
);
ALTER SEQUENCE owner_id_seq OWNED BY owner.id;

CREATE SEQUENCE company_id_seq;
CREATE TABLE company
(
id INT NOT NULL DEFAULT nextval('company_id_seq'),
name varchar(255) NOT NULL,
address varchar(255) NOT NULL,
city varchar(255) NOT NULL,
country varchar(255) NOT NULL,
email varchar(255) ,
phone varchar(255) ,
ctime timestamp NOT NULL,
utime timestamp NOT NULL,
unique (id)
);
ALTER SEQUENCE company_id_seq OWNED BY company.id;

CREATE SEQUENCE beneficial_owner_id_seq;
create table beneficial_owner
(
id int NOT NULL DEFAULT nextval('beneficial_owner_id_seq'),
cid int NOT NULL,
oid int NOT NULL,
ctime timestamp NOT NULL,
utime timestamp NOT NULL,
FOREIGN KEY (cid ) REFERENCES company(id)
ON DELETE CASCADE,
FOREIGN KEY (oid ) REFERENCES owner(id)
ON DELETE CASCADE,
UNIQUE (id)
);
ALTER SEQUENCE beneficial_owner_id_seq OWNED BY beneficial_owner.id;

commit;