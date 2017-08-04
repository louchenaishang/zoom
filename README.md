# SpringBootRest + Vue


## 开发 

``` bash

# 1.运行Application类的main方法,启动rest模块
springboot-vue/src/main/java/com/springbootvue/Application.main

# 2.进入springboot-vue/front目录
npm run dev

# 3.ui端已映射/api到rest的8080端口请求接口


```

## 发布

``` bash
# 进入springboot-vue目录打包jar文件,生成springboot-vue-xxxversion.jar
mvn clean package

# 启动tomcat
java -jar target/springboot-vue-1.0.0-SNAPSHOT.jar

```

参考 [vuejs2.0](http://cn.vuejs.org/) 

参考 [SpringBootAngularHTML5](https://github.com/mpalourdio/SpringBootAngularHTML5) 

