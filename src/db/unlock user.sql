-- Create the user 
create user APPTRACKER_OWNER identified by rcvRd34fDvAErFdfd;
-- Grant/Revoke role privileges 
grant connect to APPTRACKER_OWNER;
grant resource to APPTRACKER_OWNER;
-- Grant/Revoke system privileges 
grant unlimited tablespace to APPTRACKER_OWNER;


-- Create the user 
create user APPTRACKER_USER identified by DcgAHq8286bmNw;
-- Grant/Revoke object privileges 
grant execute on F_GET_MACHINE_ID to APPTRACKER_USER;
grant execute on P_ADD_TRACK to APPTRACKER_USER;
