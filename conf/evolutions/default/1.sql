# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table customer (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  photo_url                 varchar(255),
  photo_type                integer,
  gender                    integer,
  age                       varchar(255),
  organization              varchar(255),
  job_title                 varchar(255),
  location                  varchar(255),
  constraint ck_customer_photo_type check (photo_type in (0,1,2,3,4,5,6)),
  constraint ck_customer_gender check (gender in (0,1,2)),
  constraint pk_customer primary key (id))
;

create sequence customer_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists customer;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists customer_seq;

