@echo off

rem common tools
set JAVA=java.exe
set PYTHON=C:\Python27\python.exe
set WINBOARD=c:\WinBoard-4.8.0\WinBoard\winboard.exe

rem check java exists
where %JAVA% > nul 2> nul
if %ERRORLEVEL% NEQ 0 if not exist %JAVA% echo [ERROR] Please install Java

rem check python exists
where %PYTHON% > nul 2> nul
if %ERRORLEVEL% NEQ 0 if not exist %PYTHON% echo [ERROR] Please install Python

rem check winboard exists
where %WINBOARD% > nul 2> nul
if %ERRORLEVEL% NEQ 0 if not exist %WINBOARD% echo [ERROR] Please install WinBoard

rem engine settings
set ENGINEDIR=%~dp0
set ENGINESRC=Krudo.src
set ENGINEINI=Krudo.ini
set ENGINEVER=0.15a
set ENGINEIMG=%ENGINEDIR%logo.bmp
set ENGINELOG=Krudo.log
set ENGINETAG=Krudo %ENGINEVER%
set ENGINECMD=%JAVA% -cp %ENGINEDIR%..\build\classes org.krudo.Krudo 

rem other tools
set POLYGLOT=%WINBOARD%\..\polyglot.exe
set BUILDENV=%PYTHON% %ENGINEDIR%\tool\buildenv.py
set POSTGAME=%PYTHON% %ENGINEDIR%\tool\postgame.py

echo %POLYGLOT%