rem install parent pom ...
cd ../ms-parent
call "maven-install.cmd"

rem install ms-common jar ...
cd ../ms-common
call "maven-install.cmd"

rem packaging ms-example-service ...
cd ../ms-example-service/
mvn clean package -Dmaven.test.skip=true --settings E:\softWare\apache-maven-3.6.2\conf\settings-aliyun.xml