使用 Spring cloud Hoxton SR4、Spring boot 2.2.5、阿里巴巴 nacos组成的微服务项目
其中：
  ms-parent: 父项目，所有微服务都得父项目
  ms-common: 公共项目，包含一些公用的配置、工具类等
  ms-zuul-gateway: zuul服务网关,整合了swagger，实现各个微服务文档聚合
  ms-product-service: 基础产品服务项目，负责提供读取基础产品数据接口
  ms-crawler-service: 数据采集服务项目，负责从网站采集产品数据
  ms-translation-service: 谷歌翻译服务