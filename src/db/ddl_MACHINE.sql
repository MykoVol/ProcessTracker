-- Create table
create table MACHINE
(
  id           NUMBER generated always as identity,
  os_user_name VARCHAR2(200) not null
);
-- Add comments to the table 
comment on table MACHINE
  is 'machine where tracker are running';
-- Create/Recreate primary, unique and foreign key constraints 
alter table MACHINE
  add constraint PK_MACHINE_ID primary key (ID)
  using index;
alter table MACHINE
  add constraint AK_OS_USER unique (OS_USER_NAME)
  using index;
