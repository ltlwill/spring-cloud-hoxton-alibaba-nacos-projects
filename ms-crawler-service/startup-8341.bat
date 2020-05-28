rem 使用 -Dfile.encoding=UTF-8 参数，避免解析nacos配置中有中文的数据失败
java -Dfile.encoding=UTF-8 -jar target/ms-crawler-service-1.0.jar --server.port=8341