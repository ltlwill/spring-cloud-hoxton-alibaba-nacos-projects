rem install parent pom ...
cd ../ms-parent
call "maven-install.cmd"

rem packaging ms-admin-server ...
cd ../ms-admin-server
mvn clean package -Dmaven.test.skip=true --settings E:\softWare\apache-maven-3.6.2\conf\settings-aliyun.xml