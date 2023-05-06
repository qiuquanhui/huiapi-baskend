# 项目介绍
一个提供API接口供开发者调用的平台 参考(http://api.btstu.cn/)
用户：可以注册登录，开通接口调用权限。浏览接口并调用，且每次调用会进行统计。
管理员：可以发布接口、下线接口、接入接口，以及可视化接口的调用情况。


# 部分截图
登录主页
![img.png](img.png)
调用页面  
//todo 接口信息未完善
![img_1.png](img_1.png)

管理页面 --->需要管理员登录

1.接口管理
![img_2.png](img_2.png)

2.接口分析
![img_3.png](img_3.png)
# 业务流程

![img_5.png](img_5.png)

# 技术选型
前端

* Ant Design  Pro 
* React 
* Ant Design Procomponents 
* Umi 
* Umi Request (Axios的封装)

后端

* Java 
* Spring Boot
* MySQL 数据库
* MyBatis-Plus 及 MyBatis X 自动生成
* API 签名认证（Http 调用）
* Spring Boot Starter（SDK 开发）
* Dubbo 分布式（RPC、Nacos）
* Swagger + Knife4j 接口文档生成
* Spring Cloud Gateway 微服务网关
* Hutool、Apache Common Utils、Gson 等工具库

后端总体代码截图
![img_6.png](img_6.png)

# 主要工作
基于 Spring Boot + Dubbo + Gateway 的 API 接口调用平台。管理员可以进行接口管理，可视化接口的调用情况；
用户浏览接口并开通接口调用权限进行调用，通过客户端SDK轻松调用接口。
* 为防止接口被恶意调用，设计 API 签名认证算法，为用户分配唯一 ak / sk ，保障调用的安全性、可溯源性。
* 为了减少代码的冗余（须自己使用 HTTP + 封装签名去调用接口，平均 20 行左右代码），基于 Spring Boot Starter 开发了客户端 SDK，一行代码 即可调用接口，提高开发效率。
* 为了提高代码的可维护性，选用 Spring Cloud Gateway 作为 API 网关，实现了路由转发、访问控制、流量染色，并集中处理签名校验、请求参数校验、接口调用统计等业务逻辑，提高安全性的同时、便于系统开发维护。
* 为了提高对项目的管理，将项目后端分为 web 系统、模拟接口、公共模块、客户端 SDK、API 网关这 5 个子项目，抽象模型层和业务层代码为公共模块，大幅减少重复代码。
* 为了提高开发效率，基于自定义的 Spring boot 开发模版并配合 MyBatis X 插件自动生成后端的 CRUD 基础代码。