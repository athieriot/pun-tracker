# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "puns" ("id" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"description" VARCHAR NOT NULL,"imagePath" VARCHAR);

# --- !Downs

drop table "puns";

