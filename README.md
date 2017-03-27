# jwx(java微信公众号开发MVC框架)

> jwx是开源的java公众号开发MVC框架，基于spring配置文件和微信消息或事件注解，通过微信上下文处理一个或多个微信公众号服务请求。目的主要有两个，其一生封装微信请求xml消息为java实体对象，将返回对象转换为xml响应消息；其二是封装微信接口为java服务。微信公众号采用web服务作为消息与第三方平台发生交互，数据格式主要是xml和json，普通的web请求响应机制采用xml数据格式交互，微信接口服务采用json数据格式。jwx主要对这两个方面做了封装处理，另外借鉴springmvc的请求处理方式，以WeixinDispatcherServlet类作为消息分发控制器，通过消息组装工厂生成请求消息或事件实体，根据消息或事件类型，在消息策略处理工厂查找处理策略，获取相应的微信处理方法，Servlet获取到处理方法后，请求线程池获取线程调用微信方法，根据微信方法的返回值，生成请求的xml响应。本说明文档将分章节说明jwx框架的特征、快速入门、配置、扩展等各个方面。
## 一、特征
1. 消息重排自动处理，提供消息重排缓存接口
2. 明文/加密模式无感知切换
3. 常用的微信接口服务封装
4. 提供线程池执行微信方法，方法线程池大小可配置
5. 长任务消息推送
6. 通过微信上下文支持多个微信公众号处理
7. 提供统一的异常处理机制
8. 提供access_token自动更新机制
9. 请求消息组装
10. 灵活的响应消息类型
## 二、快速入门
本章教材提供一个最简单的例子，用户在微信公众号发一条foo的文本请求消息，公众号响应一条bar的文本响应消息。
### 1、maven配置文件设置
通过maven生成一个webapp项目，例如项目名为weixin，在maven配置文件pom.xml中添加jwx依赖，jwx的1.0.0jar包已经提交到maven中心仓库，通过[中心仓库](http://search.maven.org/)搜索jwx关键字可以获取。
<pre class=”brush: xml; gutter: true;”> 
	<dependency>
	    <groupId>com.github.jweixin</groupId>
	    <artifactId>jwx</artifactId>
	    <version>1.0.0</version>
	</dependency>
</pre>
