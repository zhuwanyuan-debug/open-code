# 图书管理系统
## 简单介绍
给予spring boot搭建的图书管理系统，提供图书增删改查、用户登陆注册的基础功能、controller权限控制、分层、
日志、缓存、数据库、配置环境隔离、swagger文档功能。

##相关技术介绍
1.mysql数据库实现增删改查
2.使用redis进行数据缓存
3.auth注解主要用于切面对controller用户权限控制
4.RedissonLock注解切面用于集群场景下的并发分布式锁控制，其中使用redission的tryLock方法，设置等待时间
5.TokenInterceptor 用户用户登陆token拦截并校验
6.LoginService进行token相关的管理，使用Jwt生成token，并将token缓存到redis，用于用户登陆成功
7.application-prd.properties、application-stg.properties 结合spring:profiles:active:
  用于开发环境和测试环境的配置隔离
8.generatorConfig.xml 用于mybatis自动生成
9.SwaggerConfig生成接口文档
10.ApiContext设置请求上下文，方便后续使用
11.ApiResult封装与端上交互的基础返回体

##使用教程
1.jdk 1.8
2.启动服务后访问 http://localhost:8080/swagger-ui.html swagger默认账号：admin 密码：123456
3.创建用户。（注:1。userName 对应数据库的email字段 2.根据用户auth = 10为超管）
4.用户账号密码登陆，登陆成功之后获取token
5.拿到token之后，可以访问图书管理bookController相关的接口

联系作者:
邮箱:jluzhuwanyuan@163.com
wx:z904973494