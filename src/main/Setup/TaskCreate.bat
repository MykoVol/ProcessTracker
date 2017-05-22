echo ^<?xml version="1.0" encoding="UTF-16"?^> > TaskSettings.xml
echo ^<Task version="1.2" xmlns="http://schemas.microsoft.com/windows/2004/02/mit/task"^> >> TaskSettings.xml
  echo ^<RegistrationInfo^> >> TaskSettings.xml
    echo ^<Date^>2017-05-14T15:21:42^</Date^> >> TaskSettings.xml
    echo ^<Author^>Palpaatin^</Author^> >> TaskSettings.xml
  echo ^</RegistrationInfo^> >> TaskSettings.xml
  echo ^<Triggers^> >> TaskSettings.xml
    echo ^<BootTrigger^> >> TaskSettings.xml
      echo ^<Enabled^>true^</Enabled^> >> TaskSettings.xml
    echo ^</BootTrigger^> >> TaskSettings.xml
  echo ^</Triggers^> >> TaskSettings.xml
  echo ^<Principals^> >> TaskSettings.xml
    echo ^<Principal id="Author"^> >> TaskSettings.xml
      echo ^<UserId^>S-1-5-18^</UserId^> >> TaskSettings.xml
      echo ^<RunLevel^>LeastPrivilege^</RunLevel^> >> TaskSettings.xml
    echo ^</Principal^> >> TaskSettings.xml
  echo ^</Principals^> >> TaskSettings.xml
  echo ^<Settings^> >> TaskSettings.xml
    echo ^<MultipleInstancesPolicy^>IgnoreNew^</MultipleInstancesPolicy^> >> TaskSettings.xml
    echo ^<DisallowStartIfOnBatteries^>false^</DisallowStartIfOnBatteries^> >> TaskSettings.xml
    echo ^<StopIfGoingOnBatteries^>false^</StopIfGoingOnBatteries^> >> TaskSettings.xml
    echo ^<AllowHardTerminate^>true^</AllowHardTerminate^> >> TaskSettings.xml
    echo ^<StartWhenAvailable^>true^</StartWhenAvailable^> >> TaskSettings.xml
    echo ^<RunOnlyIfNetworkAvailable^>false^</RunOnlyIfNetworkAvailable^> >> TaskSettings.xml
    echo ^<IdleSettings^> >> TaskSettings.xml
      echo ^<StopOnIdleEnd^>true^</StopOnIdleEnd^> >> TaskSettings.xml
      echo ^<RestartOnIdle^>false^</RestartOnIdle^> >> TaskSettings.xml
    echo ^</IdleSettings^> >> TaskSettings.xml
    echo ^<AllowStartOnDemand^>true^</AllowStartOnDemand^> >> TaskSettings.xml
    echo ^<Enabled^>true^</Enabled^> >> TaskSettings.xml
    echo ^<Hidden^>false^</Hidden^> >> TaskSettings.xml
    echo ^<RunOnlyIfIdle^>false^</RunOnlyIfIdle^> >> TaskSettings.xml
    echo ^<WakeToRun^>false^</WakeToRun^> >> TaskSettings.xml
    echo ^<ExecutionTimeLimit^>PT0S^</ExecutionTimeLimit^> >> TaskSettings.xml
    echo ^<Priority^>7^</Priority^> >> TaskSettings.xml
  echo ^</Settings^> >> TaskSettings.xml
  echo ^<Actions Context="Author"^> >> TaskSettings.xml
    echo ^<Exec^> >> TaskSettings.xml
      echo ^<Command^>javaw^</Command^> >> TaskSettings.xml
      echo ^<Arguments^>-jar ProcessTracker-1.0-SNAPSHOT.jar ^</Arguments^> >> TaskSettings.xml
      echo ^<WorkingDirectory^>%CD%^</WorkingDirectory^> >> TaskSettings.xml
    echo ^</Exec^> >> TaskSettings.xml
  echo ^</Actions^> >> TaskSettings.xml
echo ^</Task^> >> TaskSettings.xml

schtasks /create /tn "ProcessTracker" /xml TaskSettings.xml
pause