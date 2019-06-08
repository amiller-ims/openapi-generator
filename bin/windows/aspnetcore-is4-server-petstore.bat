set executable=.\modules\openapi-generator-cli\target\openapi-generator-cli.jar

If Not Exist %executable% (
  mvn clean package
)

REM set JAVA_OPTS=%JAVA_OPTS% -Xmx1024M -DloggerPath=conf/log4j.properties
set ags=generate  --artifact-id "aspnetcore-is4-petstore-server" -i modules\openapi-generator\src\test\resources\2_0\petstore.yaml -g aspnetcore-is4 -o samples\server\petstore\aspnetcore\is4

java %JAVA_OPTS% -jar %executable% %ags%
