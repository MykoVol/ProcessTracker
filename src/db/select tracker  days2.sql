select proc_name, t_date, round(c / total, 2) * 100 proc
  from (select p.proc_name,
               trunc(t.datetime) t_date,
               count(*) c,
               SUM(count(*)) OVER() total
          from proces p
          join TRACK_ENTRY t
            on p.id = t.proc_id
         where trunc(t.datetime) = trunc(sysdate)
         group by p.proc_name, trunc(t.datetime))
 order by proc desc
