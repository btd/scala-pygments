set SCRIPT_DIR=%~dp0
java -Xss4m -Xmx1G -XX:+CMSClassUnloadingEnabled -XX:MaxPermSize=256m -jar "%SCRIPT_DIR%sbt-launch.jar" %*