# jwx(java微信公众号开发MVC框架)

> jwx是开源的java公众号开发MVC框架，基于spring配置文件和微信消息或事件注解，通过微信上下文处理一个或多个微信公众号服务请求。目的主要有两个，其一生封装微信请求xml消息为java实体对象，将返回对象转换为xml响应消息；其二是封装微信接口为java服务。微信公众号采用web服务作为消息与第三方平台发生交互，数据格式主要是xml和json，普通的web请求响应机制采用xml数据格式交互，微信接口服务采用json数据格式。jwx主要对这两个方面做了封装处理，另外借鉴springmvc的请求处理方式，以WeixinDispatcherServlet类作为消息分发控制器，通过消息组装工厂生成请求消息或事件实体，根据消息或事件类型，在消息策略处理工厂查找处理策略，获取相应的微信处理方法，Servlet获取到处理方法后，请求线程池获取线程调用微信方法，根据微信方法的返回值，生成请求的xml响应。本说明文档将分章节说明jwx框架的特征、快速入门、配置、扩展等各个方面。
## 一、特征
1. 消息重排自动处理，提供消息重排缓存接口
2. 明文/加密模式无感知切换
3. 常用的微信接口服务封装
4. 提供线程池执行微信方法，方法调用线程池大小可配置
5. 长任务消息推送
6. 通过微信上下文配置支持多个微信公众号处理
7. 提供统一的异常处理机制
8. 提供access_token自动更新机制
9. 请求消息组装
10. 灵活的响应消息类型
## 二、快速入门
本章教材提供一个最简单的例子，用户在微信公众号发一条foo的文本请求消息，公众号响应一条bar的文本响应消息。
### 1、maven配置文件
通过maven生成一个webapp项目，例如项目名为weixin，在maven配置文件pom.xml中添加jwx依赖，jwx的1.0.0jar包已经提交到maven中心仓库，通过[中心仓库](http://search.maven.org/)搜索jwx关键字可以获取jar包依赖配置。

	<dependency>
	    <groupId>com.github.jweixin</groupId>
	    <artifactId>jwx</artifactId>
	    <version>1.0.0</version>
	</dependency>

### 2、web.xml文件配置
web.xml是web应用的配置文件，jwx从spring配置文件中获取配置信息，所以必须配置spring上下文环境；另外，需要配置微信消息处理分发Servlet(WeixinDispatcherServlet)，用于处理微信送过来的请求消息或事件。jwx对springmvc没有依赖关系，web mvc框架可以根据实际需要配置。

    <listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<servlet>
		<servlet-name>weixin</servlet-name>
		<servlet-class>com.github.jweixin.jwx.config.WeixinConfigurer</servlet-class>
		<load-on-startup>1</load-on-startup>
	</servlet>
	
	<servlet-mapping>
		<servlet-name>weixin</servlet-name>
		<url-pattern>/wx/*</url-pattern>
	</servlet-mapping>

- load-on-startup表示Servlet在web应用启动阶段加载，数字代表了启动次序，如果项目使用了springmvc框架，可以调整该数字为2，放到springmvc框架后面启动加载，但实际上Servlet的启动次序并没有太大的关系。
- spring配置是jwx必须的，如果没有配置spring上下文，jwx在启动阶段会报错。
- url-pattern模式匹配微信公众号平台服务器配置的URL配置，如果需要处理多个微信公众号，可以配置多个servlet-mapping或者使用路径通配符匹配多个url链接。
### 3、spring配置文件


