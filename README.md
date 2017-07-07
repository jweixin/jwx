# jwx(java微信公众号MVC开发框架)	QQ群：249705963

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
通过maven生成一个webapp项目，例如项目名为weixin，在maven配置文件pom.xml中添加jwx依赖，jwx的1.1.1jar包已经提交到maven中心仓库，通过[中心仓库](http://search.maven.org/)搜索jwx关键字可以获取jar包依赖配置。

```xml
<dependency>
    <groupId>com.github.jweixin</groupId>
    <artifactId>jwx</artifactId>
    <version>1.1.1</version>
</dependency>
```

### 2、web.xml文件配置
web.xml是web应用的配置文件，jwx从spring配置文件中获取配置信息，所以必须配置spring上下文环境；另外，需要配置微信消息处理分发Servlet(WeixinDispatcherServlet)，用于处理微信送过来的请求消息或事件。jwx对springmvc没有依赖关系，web mvc框架可以根据实际需要配置。

```xml
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<servlet>
	<servlet-name>weixin</servlet-name>
	<servlet-class>com.github.jweixin.jwx.servlet.WeixinDispatcherServlet</servlet-class>
	<load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
	<servlet-name>weixin</servlet-name>
	<url-pattern>/wx/*</url-pattern>
</servlet-mapping>
```

- load-on-startup表示Servlet在web应用启动阶段加载，数字代表了启动次序，如果项目使用了springmvc框架，可以调整该数字为2，放到springmvc框架后面启动加载，但实际上Servlet的启动次序并没有太大的关系。
- spring配置是jwx必须的，如果没有配置spring上下文，jwx在启动阶段会报错。
- url-pattern模式匹配微信公众号平台服务器配置的URL配置，如果需要处理多个微信公众号，可以配置多个servlet-mapping或者使用路径通配符匹配多个url链接。
### 3、spring配置文件
spring配置文件applicationContext.xml里面需要配置WeixinConfigurer，这是jwx唯一必须配置项，如果没有配置，启动阶段会报错。

```xml
<context:component-scan base-package="com.github.jweixin.jwx.weixin.service" />

<bean class="com.github.jweixin.jwx.config.WeixinConfigurer">
    <property name="packages">
        <list>
            <value>com.telecomjs.yc.controller</value>
        </list>
    </property>
</bean>
```

- component-scan配置了微信接口服务类，里面包含常用的微信公众号接口服务，例如菜单管理、消息服务、二维码服务、用户管理、微信网页授权、素材管理等服务内容，在web应用控制器类和微信控制器类里面可以通过@Autowired注解来注入服务。本配置并不是必须项。
- WeixinConfigurer是唯一需要配置的部分，packages属性必须配置，里面是微信控制器包路径列表，WeixinDispatcherServlet在启动阶段会扫描包路径及其下面的子包路径，如果类拥有@Weixin注解，则该类会被当作微信控制器类加载到微信上下文。
- 除了packages属性是必须配置的，其他配置都有缺省值，包括消息缓存、微信方法线程池的大小、微信方法调用超时阀值等，这部分内容放在配置部分说明了。
### 4、编写微信控制器类
当配置完上面的3个部分，所有的配置文件部分就结束了，是不是很简单呢。下面我们只需要写微信控制器类就能让我们的微信公众号活起来了。微信控制器类是用@Weixin注解的普通类，与sprngmvc里面的controller很类似，方法的执行也很类似。我们在com.telecomjs.yc.controller包下建一个java类WeixinController，如下：

```java
package com.telecomjs.yc.controller;

import com.github.jweixin.jwx.context.Weixin;
import com.github.jweixin.jwx.message.annotation.TextMsg;

@Weixin(value="/wx/coreServlet",
   appID="xxx",
   appSecret="xxx",
   encodingAESKey="xxx",
   token="xxx")
public class WeixinController {

	@TextMsg("foo")
	public String foo(){
		return "bar";
	}
}
```

- @Weixin需要配置value值，这个实际就是微信服务器配置里面URL最后的部分，**当然不包含域名和web应用的上下文**，切记，不能包含web应用上下文，其他4个部分配置内容也是公众号配置内容，我们只需要登录到公众号看下填进去就行了。如果没有配置encodingAESKey，那么是不能处理加密消息的，如果有log4j的配置文件，启动阶段会给出告警信息的。

- 同一个公众号可以配置多个@Weixin注解控制器类，其中只需要一个有其他4项配置就可以了，如果多个控制器类配置了其他4个配置项，如果相对应的配置项值不相同，启动阶段会报错。

- 不同微信公众号是通过@Weixin的value值区分的，该值同时是微信上下文的查找关键字。

- foo方法上面有@TextMsg注解，是定义的微信方法，在Servlet启动时通过包扫描加载到微信上下文对象中。jwx针对微信消息或事件类型设计了一组微信注解，基本涵盖了微信公众号定义的消息和事件类型。

- @TextMsg是文本消息注解，代表请求类型的是文本消息，value值是发送的文本消息内容。处理文本适配模式，@TextMsg还支持正则表达式适配模式，这部分内容在使用参考部分说明。

- 本例中微信方法并没有设置参数，实际可以灵活设置参数，例如我们可以在方法中设置HttpServletRequest request，HttpServletResponse response，InMessage in, WeixinContext context等参数，这部分内容也放在使用参考部分说明。

- 本例中方法的返回类型是String，代表响应的消息内容是文本消息，jwx提供了丰富的返回值类型，这部分内容会在使用参考部分详细说明。

### 5、启动web应用
  上面就是这个最简单例子的全部内容，让我们启动web应用，进入到我们的公众号，输入foo文本提交，看看返回的是不是bar这个内容了，如果是，恭喜你，你已经初步掌握了jwx的使用方法。下面更多的内容等着你呢！

## 三、配置说明
spring配置文件中唯一需要配置的bean是WeixinConfigurer类，<context:component-scan base-package="com.github.jweixin.jwx.weixin.service" />是可选配置，但里面封装了微信接口服务类，建议一定要配置进spring配置文件中。
### 1、微信接口服务
微信接口服务类位于com.github.jweixin.jwx.weixin.service包中，在spring配置文件中通过扫描包载入服务，在web mvc框架和微信控制器类中都可以通过@Autowired注解注入，与其他spring普通的服务类主键使用方式一致，服务类每个方法都有accessToken参数，这个参数指的是微信access_token，在微信控制器类方法中，可以通过设置方法的WeixinContext context参数获取，在web mvc框架中，可以通过WeixinContextHelper类的静态方法getAccessToken(String url)获取。

- CustomMsgService  客服消息服务
- MassMsgService  群发消息服务
- MaterialService  永久素材管理
- MediaService  临时素材管理
- MenuService 菜单服务
- QrcodeService 二维码服务
- TagService 标签服务
- TemplateService 模板管理及消息发送
- WebAuthService 微信网页授权服务
- UserService 微信用户服务
- SystemService 获取地址列表及长链接转短链接等其他类型服务

### 2、WeixinConfigurer配置
WeixinConfigurer是微信上下文全局配置类，里面包含了处理微信类扫描、微信消息重排处理、微信方法执行线程池大小、微信方法调用超时阀值等方面的配置，packages包扫描配置是唯一必须的配置部分，这个配置在快速入门部分已经描述，其他部分配置都有缺省配置，不是必须配置部分。
#### a、微信消息重排处理messageKeyCache配置
微信在处理消息推送时，如果没有获得响应，会隔5秒重试，最多重试3次。jwx在接到消息推送时，需要判断该消息是否已经接受过，如果接受过，则需要放弃处理。jwx设计了MessageKeyCache接口用于处理消息重排，里面需要实现唯一的方法public boolean hasMessageKey(String key);如果系统已经缓存了消息key值，返回true。jwx实现了一个默认的消息key值缓存ConcurrentHashMapMessageKeyCache。如果我们要设置缓存清理间隔，可以采用如下配置：

```xml
<context:component-scan base-package="com.github.jweixin.jwx.weixin.service" />

<bean id="messageKeyCache" class="com.github.jweixin.jwx.message.cache.ConcurrentHashMapMessageKeyCache">
	<!-- 设置消息key缓存清理间隔，单位秒 -->
	<property name="interval" value="600" />
</bean>

<bean class="com.github.jweixin.jwx.config.WeixinConfigurer">
    <property name="packages">
        <list>
            <value>com.telecomjs.yc.controller</value>
        </list>
    </property>
    
    <property name="messageKeyCache" ref="messageKeyCache" />
</bean>
```

另外我们可以实现自己的消息key缓存类，只需要实现MessageKeyCache接口就可以了，比如我们可以采用redis作为消息key值缓存数据库。
#### b、微信方法线程池大小threadPoolSize设置
微信方法是由Servlet在获取请求消息或事件的策略后取得，Servlet取得微信方法后，在线程池中获取线程执行微信方法。缺省线程池的大小是10个，如果微信公众并发比较频繁，我们可以调整线程池的大小，以提高处理效率。
如果我们调整线程池大小为100，可以采用如下配置：

```xml
<context:component-scan base-package="com.github.jweixin.jwx.weixin.service" />

<bean id="messageKeyCache" class="com.github.jweixin.jwx.message.cache.ConcurrentHashMapMessageKeyCache">
	<!-- 设置消息key缓存清理间隔，单位秒 -->
	<property name="interval" value="600" />
</bean>

<bean class="com.github.jweixin.jwx.config.WeixinConfigurer">
    <property name="packages">
        <list>
            <value>com.telecomjs.yc.controller</value>
        </list>
    </property>
    
    <property name="messageKeyCache" ref="messageKeyCache" />
    <!-- 设置微信方法执行线程池大小 -->
    <property name="threadPoolSize" value="100" />
</bean>
```

#### c、微信方法调用超时阀值weixinMethodTimeoutThreshold设置
微信推送消息或事件如果超过5秒，微信会中断连接，有时候微信方法的执行会超过5秒钟，针对这种情况，jwx采用微信方法调用超时阀值机制，如果微信方法调用线程不能在超时阀值内处理完毕，Servlet会先行返回http响应，后续Servlet会等待方法执行完毕，然后通过客服消息返回响应，对用户来说并没有感知。缺省的微信方法调用超时阀值是3000毫秒，该值可以通过配置调整，如下我们将超时阀值改成4秒：

```xml
<context:component-scan base-package="com.github.jweixin.jwx.weixin.service" />

<bean id="messageKeyCache" class="com.github.jweixin.jwx.message.cache.ConcurrentHashMapMessageKeyCache">
	<!-- 设置消息key缓存清理间隔，单位秒 -->
	<property name="interval" value="600" />
</bean>

<bean class="com.github.jweixin.jwx.config.WeixinConfigurer">
    <property name="packages">
        <list>
            <value>com.telecomjs.yc.controller</value>
        </list>
    </property>
    
    <property name="messageKeyCache" ref="messageKeyCache" />
    <!-- 设置微信方法执行线程池大小 -->
    <property name="threadPoolSize" value="100" />
    <!-- 设置微信方法调用超时阀值，单位毫秒 -->
    <property name="weixinMethodTimeoutThreshold" value="4000" />
</bean>
```

## 四、使用参考

本部分会全面讲解jwx的概念及使用方法。

### 1、主要概念

- 微信上下文：微信上下文（WexinContext）是jwx最重要的部分，jwx可以同时处理多个微信公众号，每个公众号在jwx框架中对应一个微信上下文，微信上下文持有一个微信公众号所有的配置信息及处理策略。url是微信公众号配置的服务器地址的最后部分（不包括域名和web应用上下文），是识别微信公众号的唯一标识，透过url我们可以通过微信上下文帮助类（WeixinContextHelper）的静态方法获取到微信上下文及访问token，另外，在微信方法中我们也可以通过注入WeixinContext参数来获得微信上下文。微信上下文还包含了微信的access_token、appID、appSecret、encodingAESKey这些微信公众号的配置内容。微信上下文还保存微信方法与消息注解的策略对应关系，是微信消息能够得到处理的最重要的部分。微信上下文通过@Weixin注解来配置。
- 微信消息注解：jwx定义了14个消息或事件注解，涵盖了目前所有的微信消息和事件类型，这些注解定义在包com.github.jweixin.jwx.message.annotation中，微信注解代表了消息或事件类型，可以通过微信注解配置识别请求消息类型，获取相应的微信处理方法。
- 微信方法：被微信消息注解包围的方法，通过微信方法，我们可以处理微信公众号请求消息，返回公众号的响应消息。

### 2、@Weixin注解

@Weixin是用来配置微信上下文的，该注解使用在微信控制器类上。每个被@Weixin注解包围的类会在web应用启动时被扫描，配置项会加载到微信上下文中，@Weixin注解的参数说明：

- value：代表微信上下文关键字，不能为空，在微信公众号基本配置中，处于URL配置的最后部分。例如微信公众号的URL(服务器地址)配置是：http://nalan_weixin.tunnel.qydev.com/weixin/wx/coreServlet，其中http://nalan_weixin.tunnel.qydev.com是主机栏，/weixin是web应用的上下文栏，那么value值应该是/wx/coreServlet，一个公众号可以有多个类拥有@Weixin注解，如果多个注解的value相同，则会认为是同一个微信上下文，在jwx中，区分上下文的唯一标识就是@Weixin注解的value值配置。@Weixin注解还有其他4个配置项，都有缺省值，在一个微信控制器类中配置了其他4个值，那么相同value值得控制器类只需要配置value项就可以了，如果value配置项相同，而其他4个配置项的同项配置不同，jwx在初始启动扫描阶段会给出报错提示。
- token：代表微信公众号基本配置中的Token(令牌)项的值。
- encodingAESKey：代表微信公众号基本配置中的EncodingAESKey(消息加解密密钥)，该项如果没有配置，那么jwx不能处理加密的请求消息，在jwx初始启动阶段会给出告警提示。如果我们配置了消息加解密方式为安全模式，没有配置encodingAESKey项，则运行阶段会报错。另外如果在加密请求消息到达时报如下错误：java.security.InvalidKeyException:illegal Key Size，则说明当前运行的JDK没有用JCE无限制权限策略文件替换相应的安全jar包，**解决方案：在官方网站下载JCE无限制权限策略文件（请到官网下载对应的版本， 例如JDK7的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt，如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件；如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件**。
- appID：代表微信公众号基本配置中的AppID(应用ID)。
- appSecret：代表微信公众号基本配置中的AppSecret(应用密钥)。

### 3、微信方法

在微信控制器类中，被微信消息（事件）注解包围的方法，被称为微信方法，微信方法是MVC框架里面的C部分，它控制着对请求消息的处理逻辑，并且返回响应消息。微信注解主要用于适配请求消息（事件）的类型及关键字内容，当适配成功后，由对应的微信方法执行处理逻辑，并且通过方法的返回值返回响应消息。微信方法的参数在请求消息到达时由Servlet注入，目前微信方法参数可以是HttpServletRequest request，HttpServletResponse response，InMessage in, WeixinContext context里面的任意次序和数量的组合，参数中InMessage可以是与注解对应的子类，对于@ExceptionHandler注解，可以添加Throwable及其子类作为方法参数，需要注意的是如果参数类型与实际消息类型或异常类型不能匹配，则该参数会被置为空。

### 4、微信注解

jwx定义了14个消息或事件注解，涵盖了目前所有的微信消息和事件类型，下面逐个讲解没有注解的使用。

- @TextMsg
- @ClickEvent

### 5、响应类型

