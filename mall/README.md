# mall

# 依赖的基础服务条件：


1、eureka服务；
安装方式：

2、zuul 网关服务；

安装方式：
----------------
 docker-compose   

======================




Step 0:

Step 1:

Step 2:  提测    -- 代码合并到Test的分支； 

Step 3: Jenkins  Hook   -- 构建、镜像、push docker rep  ;

Step 4: Jenkins 构建后，是否可以直接在测试机上执行 测试  版Docker Images的自动运行； 





游客进入景区内，非强制购买的商品销售功能

## 集成环境 (integrated)

1、mvn 
mvn clean package -Pintegrated docker:build
mvn clean package -Pintegrated  -Dmaven.test.skip=true docker:build -DdockerImageTags=integrated docker:push -DpushImageTags=integrated

## 测试环境（Test）:
生成Docker镜像并且上传到本地Docker仓库，以便测试人员部署开发；
效果：
Step 1 :编译,利用  profile test 配置
Step 2: 打包成Docker Image
Step 3: push 本地仓库；

mvn clean package -Ptest  -Dmaven.test.skip=true docker:build -DdockerImageTags=test docker:push -DpushImageTags=test

在测试环境里：

docker-compose 指令部署







## 生产环境（Product）：

-   mvn clean package -Pprod  -Dmaven.test.skip=true docker:build -DdockerImageTags=wzd_prod docker:push -DpushImageTags=wzd_prod



测试环境