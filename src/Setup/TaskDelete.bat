schtasks /end /TN "ProcessTracker"
taskkill /F /IM javaw.exe
schtasks /Delete /TN "ProcessTracker" /F
pause