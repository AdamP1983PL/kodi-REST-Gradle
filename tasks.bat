@echo off

echo Running runcrud.bat
call runcrud.bat

if "%ERRORLEVEL%" == "0" goto startchrome
echo Cannot start application
goto fail

:startchrome
echo Opening Chrome Browser:
start "chrome" "http://localhost:8080/crud/v1/task/tasks"

echo.
echo Work is Finished
goto end

:fail
echo.
echo Something went wrong

:end
