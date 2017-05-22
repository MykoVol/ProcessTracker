create or replace procedure p_add_track(ip_machine_id in number,
                                        ip_date_time  in timestamp with time zone,
                                        ip_proc_name  in varchar2,
                                        ip_title      in varchar2) is
  lv_id number;
begin
  begin
    select p.id
      into lv_id
      from proces p
     where p.machine_id = ip_machine_id
       and p.proc_name = ip_proc_name;
  exception
    when no_data_found then
      insert into proces p
        (p.machine_id, p.proc_name)
      values
        (ip_machine_id, ip_proc_name)
      returning id into lv_id;
  end;
  insert into track_entry
    (proc_id, title, datetime)
  values
    (lv_id, ip_title, ip_date_time);
end p_add_track;
/
