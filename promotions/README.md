# 新建微服务步骤
0. clone 本仓库dev分支代码， 并复制改名为服务名
1. 修改/pom.xml 下artifactId、name、description
2. 修改/contract/pom.xml artifactId
3. 修改/servie/pom.xml artifactId example-api(服务名-api
4. 修改ServiceExampleApplication启动类 为 Service服务名Application
5. 修改 service 服务里面 application.yml  spring.application.name=服务名_service
6. 修改 service 服务里面 对应数据库配置
7. 修改 SwaggerConfig 类，扫描包路径



其他信息:
本地开发建议连接 10.39.58.6:8849 注册中心，或自己本地启动
联调要连接 10.39.58.6:8848
