-- Create table
create table PROCES
(
  id         NUMBER generated always as identity,
  machine_id NUMBER not null,
  proc_name  VARCHAR2(200) not null
);
-- Add comments to the table 
comment on table PROCES
  is 'every process that have ever been run';
-- Create/Recreate indexes 
create index IDX_PROC_NAME on PROCES (PROC_NAME)
  tablespace USERS;
-- Create/Recreate primary, unique and foreign key constraints 
alter table PROCES
  add constraint PK_PROC_ID primary key (ID)
  using index;
alter table PROCES
  add constraint AK_PROC_NAME unique (PROC_NAME, MACHINE_ID)
  using index;
alter table PROCES
  add constraint FK_PROC_MACHINE_ID foreign key (MACHINE_ID)
  references MACHINE (ID);
