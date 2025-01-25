@echo off
set PRETTIER_PATH="C:\Users\qukhoi\OneDrive - Capgemini\TuanKhoi\prettier-3.3.3"
node %PRETTIER_PATH%\bin\prettier.js --config .prettierrc --write "src/**/*.java"
pause
