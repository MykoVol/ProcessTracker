select trunc(t.datetime) track_date,
       count(*) track_entrys,
       max(t.datetime) - min(t.datetime) tracked_time
from TRACK_ENTRY t
group by trunc(t.datetime)
order by 1 desc
