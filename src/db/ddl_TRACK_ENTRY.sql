-- Create table
create table TRACK_ENTRY
(
  proc_id  NUMBER not null,
  title    VARCHAR2(1000),
  local_datetime TIMESTAMP(6) WITH LOCAL TIME ZONE default localtimestamp not null,
  datetime date default sysdate not null
)
PARTITION BY RANGE (datetime)
INTERVAL(NUMTOYMINTERVAL(1, 'MONTH'))
(  
   PARTITION pos_data_p2 VALUES LESS THAN (TO_DATE('1-1-2017', 'DD-MM-YYYY')) 
);
-- Add comments to the table 
comment on table TRACK_ENTRY
  is 'track details, the most biggest table';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TRACK_ENTRY
  add constraint FK_TRACK_ENTRY_PROC_ID foreign key (PROC_ID)
  references PROCES (ID);
