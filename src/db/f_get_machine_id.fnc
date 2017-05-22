create or replace function f_get_machine_id(ip_os_user in varchar2)
  return number is
  lv_id number;
begin
  begin
    select id
      into lv_id
      from machine m
     where m.os_user_name = ip_os_user;
  exception
    when no_data_found then
      insert into machine m
        (m.os_user_name)
      values
        (ip_os_user)
      returning id into lv_id;
  end;
  return lv_id;
end f_get_machine_id;
/

