@echo off

for /f %%j in ("java.exe") do (
    set JAVA_HOME=%%~dp$PATH:j
)

if %JAVA_HOME%.==. (
    @echo java.exe not found
) else (
    @set MRC_VERSION="0.0.3-SNAPSHOT"
    %JAVA_HOME%\java.exe -jar mrc-%MRC_VERSION%.jar %*
)