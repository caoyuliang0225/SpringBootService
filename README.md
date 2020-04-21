# SpringBootService

这是一个以 Spring Boot 为脚手架，以 Spring Cloud 为解决方案的微服务

未来会包含多个模块，每个模块会有详细的 readme说明

mq目录目前有 RabbitMQ 和 Kafka 为基础搭建的消息中间件

eureka-server目录是所有服务的注册中心

security目录是 spring security和 oauth2+JWT



eureka             8761

mq:
kafka consumer    20001
kafka producer    20000
rabbit consumer    9001
rabbit producer    9000

security
oauth2 jwt uaa     8099
oauth2 jwt user    9090 
spring security   10000

dao
mybatis           20005

