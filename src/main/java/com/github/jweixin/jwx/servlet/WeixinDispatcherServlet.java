package com.github.jweixin.jwx.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.jweixin.jwx.aes.AesException;
import com.github.jweixin.jwx.aes.WXBizMsgCrypt;
import com.github.jweixin.jwx.config.WeixinConfigurer;
import com.github.jweixin.jwx.config.WeixinConst;
import com.github.jweixin.jwx.context.InitialWeixinConfigureException;
import com.github.jweixin.jwx.context.InvocationWeixinMethodException;
import com.github.jweixin.jwx.context.NoAccessTokenException;
import com.github.jweixin.jwx.context.Weixin;
import com.github.jweixin.jwx.context.WeixinContext;
import com.github.jweixin.jwx.context.WeixinContextConfigHelper;
import com.github.jweixin.jwx.context.WeixinInMsgHandleException;
import com.github.jweixin.jwx.context.WeixinMethod;
import com.github.jweixin.jwx.context.WeixinMethodInvocationTask;
import com.github.jweixin.jwx.context.WeixinParameterConfig;
import com.github.jweixin.jwx.context.WeixinSetting;
import com.github.jweixin.jwx.message.InMessageFactory;
import com.github.jweixin.jwx.message.cache.MessageKeyCache;
import com.github.jweixin.jwx.message.custom.CustomImageMessage;
import com.github.jweixin.jwx.message.custom.CustomMessage;
import com.github.jweixin.jwx.message.custom.CustomMusicMessage;
import com.github.jweixin.jwx.message.custom.CustomNewsMessage;
import com.github.jweixin.jwx.message.custom.CustomTextMessage;
import com.github.jweixin.jwx.message.custom.CustomVideoMessage;
import com.github.jweixin.jwx.message.custom.CustomVoiceMessage;
import com.github.jweixin.jwx.message.request.InMessage;
import com.github.jweixin.jwx.message.request.InPlainMessage;
import com.github.jweixin.jwx.message.response.Article;
import com.github.jweixin.jwx.message.response.Image;
import com.github.jweixin.jwx.message.response.Music;
import com.github.jweixin.jwx.message.response.OutArticleMessage;
import com.github.jweixin.jwx.message.response.OutImageMessage;
import com.github.jweixin.jwx.message.response.OutMessage;
import com.github.jweixin.jwx.message.response.OutMusicMessage;
import com.github.jweixin.jwx.message.response.OutTextMessage;
import com.github.jweixin.jwx.message.response.OutVideoMessage;
import com.github.jweixin.jwx.message.response.OutVoiceMessage;
import com.github.jweixin.jwx.message.response.Success;
import com.github.jweixin.jwx.message.response.Text;
import com.github.jweixin.jwx.message.response.Video;
import com.github.jweixin.jwx.message.response.Voice;
import com.github.jweixin.jwx.message.strategy.MsgStrategy;
import com.github.jweixin.jwx.message.strategy.MsgStrategyFactory;
import com.github.jweixin.jwx.sign.SignUtil;
import com.github.jweixin.jwx.util.ClasspathPackageScanner;
import com.github.jweixin.jwx.util.StringUtil;
import com.github.jweixin.jwx.util.WeixinInterfaceException;
import com.github.jweixin.jwx.util.WeixinInterfaceHelper;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;

/**
 * 微信url分发servlet
 * 
 * @author zzx
 * @version 1.0 
 * 2017-1-12 初始开发 
 * 2017-1-23 增加子线程调度微信方法部分 
 * 2017-2-4 实现长执行通过微信客服接口返回结果 
 * 2017-2-27 增加缺省消息或事件处理 
 * 2017-3-2 增加方法调用异常处理 
 * 2017-3-9 增加线程池执行微信方法部分 
 * 2017-5-11 修改包扫描获取微信注解类部分
 * 2017-7-4 增加spring可以配置微信基本参数部分
 */
public class WeixinDispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(WeixinDispatcherServlet.class);

	/**
	 * web应用的spring容器上下文
	 */
	private WebApplicationContext webApplicationContext;

	/**
	 * 请求消息工厂
	 */
	private InMessageFactory inFactory = InMessageFactory.getInstance();

	/**
	 * 消息策略工厂
	 */
	private MsgStrategyFactory strategyFactory = MsgStrategyFactory.getInstance();
	/**
	 * 消息key值缓存，用于消息重排
	 */
	private MessageKeyCache messageKeyCache;
	/**
	 * 微信方法执行线程池
	 */
	private ExecutorService executorService;
	/**
	 * url链接与微信上下文的映射关系 微信上下文在servlet初始化时装载配置
	 */
	private Map<String, WeixinContext> contextMapper = new HashMap<String, WeixinContext>();
	/**
	 * 微信方法调用超时阀值
	 */
	private long weixinMethodTimeoutThreshold;
	/**
	 * 微信公众基本配置
	 */
	private WeixinParameterConfig weixinParameterConfig;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// 获取servlet容器的spring配置上下文
		webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

		// 获取上下文里面的微信包扫描配置
		WeixinConfigurer weixinConfig = webApplicationContext.getBean(WeixinConfigurer.class);
		if (weixinConfig == null || weixinConfig.getPackages() == null || weixinConfig.getPackages().size() == 0) {
			throw new InitialWeixinConfigureException("spring配置文件中缺少微信包扫描配置");
		}

		// 去除重复的包名和子包名
		List<String> packageNames = weixinConfig.getPackages();
		List<String> candidatePackages = new ArrayList<String>();

		Iterator<String> iter = packageNames.iterator();
		while (iter.hasNext()) {
			String packageName = iter.next().trim();
			boolean include = false;
			Iterator<String> it = candidatePackages.iterator();
			while (it.hasNext()) {
				String pkgName = it.next();
				// 如果候选包名等于本次迭代的包名或者是本次迭代包的父包，则跳过
				if (pkgName.equals(packageName) || packageName.startsWith(pkgName + ".")) {
					include = true;
					break;
				} else if (pkgName.startsWith(packageName + ".")) { // 如果候选包名是本次迭代的子包名，则用候选包名被替换
					candidatePackages.set(candidatePackages.indexOf(pkgName), packageName);
					include = true;
				}
			}
			if (!include) {
				candidatePackages.add(packageName);
			}
		}
		
		List<String> scanPackages = StringUtil.removeDuplicate(candidatePackages);

		ClasspathPackageScanner scanner = new ClasspathPackageScanner(scanPackages, Weixin.class);

		Set<Class<?>> wxClassList;
		try {
			wxClassList = scanner.getClassSet();
		} catch (ClassNotFoundException | IOException e) {
			throw new InitialWeixinConfigureException("加载微信类发生异常", e);
		}

		Iterator<Class<?>> it = wxClassList.iterator();

		while (it.hasNext()) {
			Class<?> clazz = it.next();
			logger.debug("解析微信类:" + clazz.getName());
			parseWeixinClass(clazz);
		}
		
		// 获取微信公众号的基本配置
		weixinParameterConfig = weixinConfig.getWeixinParameterConfig();
		
		if(weixinParameterConfig != null){
			List<WeixinSetting> list = weixinParameterConfig.getWeixinSettings();
			if(list != null && list.size()>0){
				Iterator<WeixinSetting> iterator = list.iterator();
				while(iterator.hasNext()){
					WeixinSetting setting = iterator.next();
					String url = setting.getUrl();
					if(!StringUtil.isNull(url)){
						WeixinContext context = contextMapper.get(url);
						if(context!=null){
							// 配置上下文的encodingAESKey值
							String encodingAESKey = setting.getEncodingAESKey();
							if (!StringUtil.isNull(encodingAESKey)) {
								context.setEncodingAESKey(encodingAESKey);
							}

							// token配置
							String token = setting.getToken();
							if (!StringUtil.isNull(token)) {
								context.setToken(token);
							}

							// 开发者应用ID配置
							String appID = setting.getAppID();
							if (!StringUtil.isNull(appID)) {
								context.setAppID(appID);
							}

							// 开发者应用密钥配置
							String appSecret = setting.getAppSecret();
							if (!StringUtil.isNull(appSecret)) {
								context.setAppSecret(appSecret);
							}
						}
					}
				}
			}
		}

		// 迭代处理生成微信上下文消息加密工具WXBizMsgCrypt
		Iterator<Entry<String, WeixinContext>> iterator = contextMapper.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, WeixinContext> entry = iterator.next();
			WeixinContext context = entry.getValue();
			String token = context.getToken();
			String encodingAESKey = context.getEncodingAESKey();
			String appID = context.getAppID();
			if (StringUtil.isNull(token) || StringUtil.isNull(encodingAESKey) || StringUtil.isNull(appID)) {
				logger.warn("微信上下文(" + entry.getKey() + ")缺少必要的配置信息,不能处理加密的微信消息");
			} else {
				context.setPc(new WXBizMsgCrypt(token, encodingAESKey, appID));
			}
		}

		// 配置微信消息缓存
		messageKeyCache = weixinConfig.getMessageKeyCache();

		// 配置微信方法执行线程池大小
		executorService = Executors.newFixedThreadPool(weixinConfig.getThreadPoolSize());

		// 配置微信方法调用超时阀值
		weixinMethodTimeoutThreshold = weixinConfig.getWeixinMethodTimeoutThreshold();

		// 将上下文对象暴露给微信上下文帮助类
		WeixinContextHelper.setContextMapper(contextMapper);
	}

	/**
	 * 解析微信类
	 * 
	 * @param clazz
	 * @throws ServletException
	 * @throws MultiWeixinEncodingAESKeyException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void parseWeixinClass(Class<?> clazz) throws ServletException {
		Weixin wx = clazz.getAnnotation(Weixin.class);
		String url = wx.value();
		// 获取url对应的微信上下文，如果不存在，就新建一个
		WeixinContext context = contextMapper.get(url);
		if (context == null) {
			context = new WeixinContext();
			logger.debug("新建微信上下文(" + url + ")");
			contextMapper.put(url, context);
		}

		// 获取微信上下文的url，如果为空，则赋值
		if (StringUtil.isNull(context.getUrl())) {
			context.setUrl(url);
		}
		
		WeixinSetting setting = new WeixinSetting();
		setting.setUrl(url);
		setting.setAppID(wx.appID());
		setting.setAppSecret(wx.appSecret());
		setting.setEncodingAESKey(wx.encodingAESKey());
		setting.setToken(wx.token());
		
		//配置微信参数
		setContextParameter(context, setting);

		Object wxObj;
		try {
			// 生成微信对象实例
			wxObj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new InitialWeixinConfigureException("实例化微信对象异常", e);
		}

		// 注入spring服务
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(wxObj, getServletContext());

		Method[] methods = clazz.getDeclaredMethods();
		// 解析微信方法
		for (Method method : methods) {
			if (WeixinMethod.hasWeixinAnnotationType(method)) {
				logger.debug("解析微信上下文(" + url + ")微信方法:" + method.getName());
				parseWeixinMethod(context, method, wxObj);
			}
		}
	}
	
	/**
	 * 设置微信上下文基本设置
	 * @param context
	 * @param setting
	 * @throws InitialWeixinConfigureException
	 */
	private void setContextParameter(WeixinContext context, WeixinSetting setting) throws InitialWeixinConfigureException{
		// 配置上下文的encodingAESKey值
		String encodingAESKey = setting.getEncodingAESKey();
		if (!StringUtil.isNull(encodingAESKey)) {
			WeixinContextConfigHelper.setFieldValue(context, "encodingAESKey", encodingAESKey);
		}

		// token配置
		String token = setting.getToken();
		if (!StringUtil.isNull(token)) {
			WeixinContextConfigHelper.setFieldValue(context, "token", token);
		}

		// 开发者应用ID配置
		String appID = setting.getAppID();
		if (!StringUtil.isNull(appID)) {
			WeixinContextConfigHelper.setFieldValue(context, "appID", appID);
		}

		// 开发者应用密钥配置
		String appSecret = setting.getAppSecret();
		if (!StringUtil.isNull(appSecret)) {
			WeixinContextConfigHelper.setFieldValue(context, "appSecret", appSecret);
		}
	}

	/**
	 * 解析微信方法
	 * 
	 * @param context
	 * @param method
	 * @param wxObj
	 * @throws ServletException
	 */
	private void parseWeixinMethod(WeixinContext context, Method method, Object wxObj) throws ServletException {
		WeixinMethod wxMethod = new WeixinMethod();
		wxMethod.setWxObj(wxObj);
		wxMethod.setWxMethod(method);
		Class<?>[] params = method.getParameterTypes();
		// 检查方法参数类型
		for (Class<?> param : params) {
			if (!wxMethod.isLegalParamType(param)) {
				throw new InitialWeixinConfigureException(param.getName() + "不是合法的微信方法参数类型");
			}
		}
		wxMethod.setParams(params);

		// 检查返回值类型
		Class<?> returnType = method.getReturnType();
		if (!wxMethod.isLegalReturnType(returnType)) {
			throw new InitialWeixinConfigureException(returnType.getName() + "不是合法的微信方法返回值类型");
		}
		wxMethod.setReturnType(returnType);

		// 处理方法注解信息
		Annotation[] annos = method.getAnnotations();
		for (Annotation anno : annos) {
			Class<? extends Annotation> annotationType = anno.annotationType();
			if (WeixinMethod.isWeixinAnnotationType(annotationType)) {
				if (WeixinMethod.isExceptionAnnotationType(annotationType)) {
					// 异常处理注解构建
					strategyFactory.getExceptionHandlerStrategy().buildExceptionHandlerMethodMapper(context, wxMethod,
							anno);
				} else {
					// 处理注解，获取注解类型的处理策略
					MsgStrategy strategy = strategyFactory.annotationStrategyCreator(annotationType);
					// 建立消息与微信方法的关联映射
					strategy.buildMsgMethodMapper(context, wxMethod, anno);
				}
			}
		}
	}

	/**
	 * 微信url签名验证
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		WeixinContext context = getWeixinContext(request);
		String contextUrl = request.getRequestURI().substring(request.getContextPath().length());
		if (context == null) {
			logger.error(contextUrl + "没有配置微信上下文");
		} else if (StringUtil.isNull(context.getToken())) {
			logger.error("微信上下文" + contextUrl + "没有配置token");
		} else {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");
			// 通过检验 signature 对请求进行校验， 若校验成功则原样返回 echostr， 表示接入成功，否则接入失败
			if (SignUtil.checkSignature(context.getToken(), signature, timestamp, nonce)) {
				logger.info("微信上下文" + contextUrl + "验证成功");
				out.print(echostr);
			} else {
				logger.warn("微信上下文" + contextUrl + "验证失败");
			}
		}
		if (out != null) {
			out.close();
		}
	}

	/**
	 * 获取请求的微信上下文
	 * 
	 * @param req
	 * @return
	 */
	private WeixinContext getWeixinContext(HttpServletRequest request) {
		String contextUrl = request.getRequestURI().substring(request.getContextPath().length());
		return contextMapper.get(contextUrl);
	}

	/**
	 * 响应微信消息或事件请求
	 * 
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 将请求、响应的编码均设置为 UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = response.getWriter();

		// 获取微信应用的上下文url
		String contextUrl = request.getRequestURI().substring(request.getContextPath().length());

		// 获取微信上下文
		WeixinContext context = getWeixinContext(request);
		if (context == null) {
			logger.error(contextUrl + "没有找到配置的微信上下文对象");
			out.print("success");
			if (out != null) {
				out.close();
			}
		} else {
			// 消息是否加密
			boolean isEncrypted = false;
			// 微信加密签名
			String msgSignature = request.getParameter("msg_signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");

			// 获取请求原始xml内容
			String xmlText = getRequestOriginalXml(request);

			// 判断是否为加密消息类型
			String encryptType = request.getParameter("encrypt_type");
			if (encryptType != null && "aes".equals(encryptType)) {
				isEncrypted = true;
				// 获取微信上下文WXBizMsgCrypt
				WXBizMsgCrypt pc = context.getPc();
				// 如果WXBizMsgCrypt为空，则抛出异常
				if (pc == null) {
					throw new WeixinInMsgHandleException("微信上下文(" + contextUrl + ")没有找到WXBizMsgCrypt对象,不能处理加密的微信消息");
				} else {
					xmlText = pc.decryptMsg(msgSignature, timestamp, nonce, xmlText);
				}
			}

			// System.out.println(xmlText);

			// 生成消息类型
			InMessage in = null;
			try {
				in = inFactory.creator(xmlText);
			} catch (DocumentException | WeixinInMsgHandleException e) {
				if (out != null) {
					out.close();
				}
				throw new WeixinInMsgHandleException("解析微信请求的xml文本发生异常", e);
			}

			// 生成消息的key值
			String msgKey = contextUrl + "-";
			if (in instanceof InPlainMessage) {
				InPlainMessage plain = (InPlainMessage) in;
				msgKey = msgKey + plain.getMsgId();
			} else {
				msgKey = msgKey + in.getFromUserName() + "-" + in.getCreateTime();
			}

			// 如果缓存中有存在这个key值，说明本次消息是重发的，则不处理这条消息，直接返回
			if (messageKeyCache.hasMessageKey(msgKey)) {
				out.print("success");
				if (out != null) {
					out.close();
				}
			} else {
				// 获取消息处理策略
				MsgStrategy strategy = strategyFactory.inStrategyCreator(in.getClass());
				// 从消息策略中查找微信方法
				WeixinMethod method = strategy.getInMsgWeixinMethod(context, in);
				if (method == null) {
					// 获取缺省微信处理方法
					method = strategyFactory.getDefaultStrategy().getInMsgWeixinMethod(context, in);
					if (method == null) {
						logger.warn(
								"微信上下文(" + contextUrl + ")没有找到消息或事件" + in.getClass().getName() + "的处理方法:" + xmlText);
						out.print("success");
						if (out != null) {
							out.close();
						}
					}
				}
				if (method != null) {
					// 调用微信方法
					invokeWeixinMethod(method, request, response, in, context, out, isEncrypted, timestamp, nonce);
				}
			}
		}
	}

	/**
	 * 调用微信方法处理请求消息或事件
	 * 
	 * @param method
	 * @param request
	 * @param response
	 * @param in
	 * @param context
	 * @param out
	 * @param isEncrypted
	 * @param timestamp
	 * @param nonce
	 * @throws ServletException
	 */
	private void invokeWeixinMethod(WeixinMethod method, HttpServletRequest request, HttpServletResponse response,
			InMessage in, WeixinContext context, PrintWriter out, boolean isEncrypted, String timestamp, String nonce)
			throws ServletException {

		// 生成方法执行的参数
		Class<?>[] params = method.getParams();
		Object[] args = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			if (params[i].isInstance(request)) {
				args[i] = request;
			} else if (params[i].isInstance(response)) {
				args[i] = response;
			} else if (params[i].isInstance(in)) {
				args[i] = in;
			} else if (params[i].isInstance(context)) {
				args[i] = context;
			} else {
				args[i] = null;
			}
		}

		// 生成子线程执行微信方法
		WeixinMethodInvocationTask task = new WeixinMethodInvocationTask(method.getWxObj(), method.getWxMethod(), args);
		Future<Object> future = executorService.submit(task);

		// 获取子线程方法执行后的返回对象
		Object retObj = null;
		try {
			try {
				retObj = future.get(weixinMethodTimeoutThreshold, TimeUnit.MILLISECONDS);
				returnRespMsg(retObj, out, in, isEncrypted, context, timestamp, nonce);
			} catch (InterruptedException e1) {
				// 如果方法执行发生异常
				throw new InvocationWeixinMethodException("调用微信方法发生中断异常", e1);
			} catch (ExecutionException e1) {
				// 处理线程执行时异常
				handleExecutionException(e1, context, request, response, in, out, isEncrypted, timestamp, nonce,
						"main");
			}
		} catch (TimeoutException e) {
			// 如果子线程调度微信方法执行超过了指定的时间阀值，返回空消息
			out.print("success");
			if (out != null) {
				out.close();
			}
			try {
				// 继续等待子线程执行完毕，获取方法的返回值对象
				retObj = future.get();
				// 发送客服消息
				sendCustomMsg(retObj, context, in, method);
			} catch (InterruptedException e1) {
				// 如果方法执行发生异常
				throw new InvocationWeixinMethodException("调用微信方法发生中断异常", e1);
			} catch (ExecutionException e1) {
				// 处理线程执行时异常
				handleExecutionException(e1, context, request, response, in, out, isEncrypted, timestamp, nonce,
						"custom");
			}
		}
	}

	/**
	 * 返回响应消息
	 * 
	 * @param retObj
	 * @param out
	 * @param in
	 * @param isEncrypted
	 * @param context
	 * @param timestamp
	 * @param nonce
	 * @throws WeixinInMsgHandleException
	 * @throws AesException
	 */
	private void returnRespMsg(Object retObj, PrintWriter out, InMessage in, boolean isEncrypted, WeixinContext context,
			String timestamp, String nonce) throws WeixinInMsgHandleException, AesException {
		if (retObj != null) {
			if (retObj instanceof Success) {
				out.print(Success.SUCCESS.value());
			} else {
				String replyXml = getReplyXml(retObj, in);
				if (isEncrypted) {
					replyXml = context.getPc().encryptMsg(replyXml, timestamp, nonce);
				}
				out.print(replyXml);
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 发送客服消息
	 * 
	 * @param retObj
	 * @param context
	 * @param in
	 * @param method
	 * @throws NoAccessTokenException
	 * @throws WeixinInMsgHandleException
	 */
	private void sendCustomMsg(Object retObj, WeixinContext context, InMessage in, WeixinMethod method)
			throws WeixinInMsgHandleException {
		if (!(retObj instanceof Success)) {
			// 通过客服消息将执行结果通知用户
			String customUrl = WeixinConst.WEIXIN_CUSTOM_MESSAGE_SEND_LINK + "?access_token="
					+ context.getAccessToken();
			try {
				ReturnCode rc = WeixinInterfaceHelper.post(customUrl, getReplyJson(retObj, in), ReturnCode.class);
				if (!rc.check()) {
					logger.error("客服方法调用失败，无法向客户" + in.getFromUserName() + "返回方法" + method.getWxMethod().getName()
							+ "的执行结果，返回码:" + rc.getErrcode() + ",返回消息:" + rc.getErrmsg());
				}
			} catch (WeixinInterfaceException e) {
				Throwable throwable = e.getRootCause();
				if (throwable != null) {
					logger.error("执行客服消息发送失败:" + throwable.getMessage());
				} else {
					logger.error("执行客服消息发送失败:" + e.getMessage());
				}
			}
		}
	}

	/**
	 * 处理微信方法执行时抛出的异常
	 * 
	 * @param ex
	 *            线程执行异常
	 * @throws InvocationWeixinMethodException
	 */
	private void handleExecutionException(ExecutionException ex, WeixinContext context, HttpServletRequest request,
			HttpServletResponse response, InMessage in, PrintWriter out, boolean isEncrypted, String timestamp,
			String nonce, String phase) throws InvocationWeixinMethodException {
		// 从线程执行异常中获取方法反射执行异常
		Throwable throwable = ex.getCause();

		// 如果线程抛出的不是InvocationTargetException异常，则将该异常直接抛出
		if (!(throwable instanceof InvocationTargetException)) {
			if (out != null) {
				out.close();
			}
			throw new InvocationWeixinMethodException("调用微信方法线程发生异常", throwable);
		}
		// 获取反射调用异常
		InvocationTargetException ite = (InvocationTargetException) throwable;
		// 微信方法抛出的原始异常
		Throwable me = ite.getCause();

		// 从异常处理策略中查找异常处理方法
		WeixinMethod wxMethod = strategyFactory.getExceptionHandlerStrategy().getExceptionHandlerMethod(context, me);
		// 如果没有找到异常处理方法，这将这个方法异常抛出
		if (wxMethod == null) {
			if (out != null) {
				out.close();
			}
			throw new InvocationWeixinMethodException("调用微信方法执行发生异常", me);
		}

		// 生成异常处理方法执行的参数
		Class<?>[] params = wxMethod.getParams();
		Object[] args = new Object[params.length];
		for (int i = 0; i < params.length; i++) {
			if (params[i].isInstance(request)) {
				if ("custom".equals(phase)) {
					args[i] = null;
				} else {
					args[i] = request;
				}
			} else if (params[i].isInstance(response)) {
				if ("custom".equals(phase)) {
					args[i] = null;
				} else {
					args[i] = response;
				}
			} else if (params[i].isInstance(in)) {
				args[i] = in;
			} else if (params[i].isInstance(context)) {
				args[i] = context;
			} else if (params[i].isInstance(me)) {
				args[i] = me;
			} else {
				args[i] = null;
			}
		}

		// 执行异常处理方法
		try {
			Object obj = wxMethod.getWxMethod().invoke(wxMethod.getWxObj(), args);
			if ("main".equals(phase)) {
				try {
					returnRespMsg(obj, out, in, isEncrypted, context, timestamp, nonce);
				} catch (WeixinInMsgHandleException | AesException e1) {
					if (out != null) {
						out.close();
					}
					throw new InvocationWeixinMethodException("返回微信异常响应消息发生异常", e1);
				}
			} else if ("custom".equals(phase)) {
				try {
					sendCustomMsg(obj, context, in, wxMethod);
				} catch (WeixinInMsgHandleException e1) {
					throw new InvocationWeixinMethodException("通过客服方法发送异常消息发生异常", e1);
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			if (out != null) {
				out.close();
			}
			throw new InvocationWeixinMethodException("调用微信异常处理方法发生异常", e);
		}
	}

	/**
	 * 读取post请求原始xml内容
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private String getRequestOriginalXml(HttpServletRequest request) throws IOException {
		BufferedReader in = new BufferedReader(
				new InputStreamReader(request.getInputStream(), Charset.forName("UTF-8")));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = in.readLine()) != null) {
			sb.append(line);

		}
		return sb.toString();
	}

	/**
	 * 产生微信方法执行返回对象的xml文本
	 * 
	 * @param retObj
	 * @return
	 * @throws WeixinInMsgHandleException
	 */
	private String getReplyXml(Object retObj, InMessage in) throws WeixinInMsgHandleException {
		if (retObj instanceof OutMessage) {
			return retObj.toString();
		} else if (retObj instanceof String) {
			String content = (String) retObj;
			OutMessage textMsg = new OutTextMessage(in, content);
			return textMsg.toString();
		} else if (retObj instanceof Text) {
			Text text = (Text) retObj;
			OutMessage textMsg = new OutTextMessage(in, text);
			return textMsg.toString();
		} else if (retObj instanceof Image) {
			Image image = (Image) retObj;
			OutMessage imageMsg = new OutImageMessage(in, image);
			return imageMsg.toString();
		} else if (retObj instanceof Voice) {
			Voice voice = (Voice) retObj;
			OutVoiceMessage voiceMsg = new OutVoiceMessage(in, voice);
			return voiceMsg.toString();
		} else if (retObj instanceof Video) {
			Video video = (Video) retObj;
			OutVideoMessage videoMsg = new OutVideoMessage(in, video);
			return videoMsg.toString();
		} else if (retObj instanceof Music) {
			Music music = (Music) retObj;
			OutMusicMessage musicMsg = new OutMusicMessage(in, music);
			return musicMsg.toString();
		} else if (retObj instanceof Article) {
			Article article = (Article) retObj;
			OutArticleMessage articleMsg = new OutArticleMessage(in);
			articleMsg.getArticles().add(article);
			return articleMsg.toString();
		} else if (retObj.getClass().isArray()) {
			Article[] articles = (Article[]) retObj;
			ArrayList<Article> arrayList = new ArrayList<Article>(Arrays.asList(articles));
			OutArticleMessage articleMsg = new OutArticleMessage(in);
			articleMsg.setArticles(arrayList);
			return articleMsg.toString();
		} else if (retObj instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Article> articles = (Collection<Article>) retObj;
			OutArticleMessage articleMsg = new OutArticleMessage(in);
			List<Article> articleList = articleMsg.getArticles();
			Iterator<Article> iter = articles.iterator();
			while (iter.hasNext()) {
				Article article = iter.next();
				articleList.add(article);
			}
			return articleMsg.toString();
		} else {
			throw new WeixinInMsgHandleException("无法识别的返回值对象");
		}
	}

	/**
	 * 产生微信客服接口发送消息对象
	 * 
	 * @param retObj
	 * @param in
	 * @return
	 * @throws WeixinInMsgHandleException
	 */
	private CustomMessage getReplyJson(Object retObj, InMessage in) throws WeixinInMsgHandleException {
		if (retObj instanceof String) {
			String content = (String) retObj;
			CustomTextMessage textMsg = new CustomTextMessage(in, content);
			return textMsg;
		} else if (retObj instanceof Text) {
			Text text = (Text) retObj;
			CustomTextMessage textMsg = new CustomTextMessage(in, text);
			return textMsg;
		} else if (retObj instanceof OutTextMessage) {
			OutTextMessage text = (OutTextMessage) retObj;
			CustomTextMessage textMsg = new CustomTextMessage(in, text.getText());
			return textMsg;
		} else if (retObj instanceof Image) {
			Image image = (Image) retObj;
			CustomImageMessage imageMsg = new CustomImageMessage(in, image);
			return imageMsg;
		} else if (retObj instanceof OutImageMessage) {
			OutImageMessage outImage = (OutImageMessage) retObj;
			CustomImageMessage imageMsg = new CustomImageMessage(in, outImage.getImage());
			return imageMsg;
		} else if (retObj instanceof Voice) {
			Voice voice = (Voice) retObj;
			CustomVoiceMessage voiceMsg = new CustomVoiceMessage(in, voice);
			return voiceMsg;
		} else if (retObj instanceof OutVoiceMessage) {
			OutVoiceMessage outVoice = (OutVoiceMessage) retObj;
			CustomVoiceMessage voiceMsg = new CustomVoiceMessage(in, outVoice.getVoice());
			return voiceMsg;
		} else if (retObj instanceof Video) {
			Video video = (Video) retObj;
			CustomVideoMessage videoMsg = new CustomVideoMessage(in, video);
			return videoMsg;
		} else if (retObj instanceof OutVideoMessage) {
			OutVideoMessage outVideo = (OutVideoMessage) retObj;
			CustomVideoMessage videoMsg = new CustomVideoMessage(in, outVideo.getVideo());
			return videoMsg;
		} else if (retObj instanceof Music) {
			Music music = (Music) retObj;
			CustomMusicMessage musicMsg = new CustomMusicMessage(in, music);
			return musicMsg;
		} else if (retObj instanceof OutMusicMessage) {
			OutMusicMessage outMusic = (OutMusicMessage) retObj;
			CustomMusicMessage musicMsg = new CustomMusicMessage(in, outMusic.getMusic());
			return musicMsg;
		} else if (retObj instanceof Article) {
			Article article = (Article) retObj;
			List<Article> articles = new ArrayList<Article>();
			articles.add(article);
			CustomNewsMessage newsMsg = new CustomNewsMessage(in, articles);
			return newsMsg;
		} else if (retObj instanceof OutArticleMessage) {
			OutArticleMessage outArticle = (OutArticleMessage) retObj;
			List<Article> articles = outArticle.getArticles();
			if (articles != null && articles.size() > 8) {
				articles = articles.subList(0, 8);
			}
			CustomNewsMessage newsMsg = new CustomNewsMessage(in, articles);
			return newsMsg;
		} else if (retObj.getClass().isArray()) {
			Article[] articles = (Article[]) retObj;
			List<Article> articleList = new ArrayList<Article>(Arrays.asList(articles));
			if (articleList != null && articleList.size() > 8) {
				articleList = articleList.subList(0, 8);
			}
			CustomNewsMessage newsMsg = new CustomNewsMessage(in, articleList);
			return newsMsg;
		} else if (retObj instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<Article> articles = (Collection<Article>) retObj;
			List<Article> articleList = new ArrayList<Article>();
			Iterator<Article> iter = articles.iterator();
			while (iter.hasNext()) {
				Article article = iter.next();
				articleList.add(article);
			}
			if (articleList != null && articleList.size() > 8) {
				articleList = articleList.subList(0, 8);
			}
			CustomNewsMessage newsMsg = new CustomNewsMessage(in, articleList);
			return newsMsg;
		} else {
			throw new WeixinInMsgHandleException("无法识别的返回值对象");
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		// 关闭线程池
		executorService.shutdown();
	}

}
