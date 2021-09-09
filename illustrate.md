http://localhost:8300/swagger-ui.html

http://localhost:8200/  monitor控制台 账号：admin 密码：admin

http://1.14.153.121:8080/#/login   sentinel控制台 账号：sentinel 密码：sentinel

http://1.14.153.121:8848/nacos/index.html  nacos控制台 账号：nacos 密码：nacos

http://1.14.153.121:9000   minio控制台   账号：minioadmin 密码：minioadmin

已完成：
在服务器上完成nacos、sentinel、minio、oracle、redis的安装
sentinel持久化规则到nacos并存储到mysql数据库
sentinel和nacos可互相读取规则数据

基础框架
-quick-spring-model                      父项目
---quick-clinical                        业务模块
-----quick-clinical-interface            业务模块对外提供的feign接口
-----quick-clinical-interface            业务模块主要业务
---quick-common                          公共模块
---quick-external                        第三方业务接入模块
-----quick-external-interface            第三方模块对外提供的feign接口
-----quick-external-service              第三方模块主要业务
---quick-gateway                         网关模块，主要负责token鉴权，转发
---quick-laboratory                      业务模块
-----quick-laboratory-interface          业务模块对外提供的feign接口
-----quick-laboratory-service            业务模块主要业务
---quick-monitor                         监控模块，监控各服务健康情况
---quick-nursing                         业务模块
-----quick-nursing-interface             业务模块对外提供的feign接口
-----quick-nursing-service               业务模块主要业务
---quick-user                            用户模块，负责产生用户的token


