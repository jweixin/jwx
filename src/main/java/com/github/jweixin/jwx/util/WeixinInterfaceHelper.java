package com.github.jweixin.jwx.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.github.jweixin.jwx.weixin.entity.BytesFile;
import com.github.jweixin.jwx.weixin.entity.ReturnCode;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractButton;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractButtonAdapter;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractMenu;
import com.github.jweixin.jwx.weixin.entity.menu.AbstractMenuAdapter;
import com.github.jweixin.jwx.weixin.entity.menu.Button;
import com.github.jweixin.jwx.weixin.entity.menu.ButtonAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.MultipartBody.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 微信接口工具类 封装了通过http协议获取微信服务的结果
 * 
 * @author Administrator
 *
 */
public class WeixinInterfaceHelper {

	/**
	 * 设置json命名策略，java对象域驼峰格式，json字符串是小写下划线格式
	 */
	private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(AbstractButton.class, new AbstractButtonAdapter())
			.registerTypeAdapter(Button.class, new ButtonAdapter())
			.registerTypeAdapter(AbstractMenu.class, new AbstractMenuAdapter()).create();

	/**
	 * OkHttpClient客户端对象，代表发送http请求的客户端
	 */
	private static OkHttpClient client = new OkHttpClient.Builder().build();

	/**
	 * JSON媒体介质类型
	 */
	public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; encoding=utf-8");

	/**
	 * 缓存大小
	 */
	private final static int BUFFER_SIZE = 4096;

	/**
	 * 获取get请求返回的json字符串对应的类型对象
	 * 
	 * @param url
	 * @param classOfT
	 * @return
	 */
	public static <T> T get(String url, Class<T> classOfT) {
		Request request = new Request.Builder().url(url).addHeader("Accept", "application/json").build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String jsonStr = response.body().string();
				// 如果返回值类型不是ReturnCode及其子类
				if (!ReturnCode.class.isAssignableFrom(classOfT)) {
					checkErrCode(jsonStr, "执行链接[" + url + "]的GET请求返回微信错误码");
				}
				return gson.fromJson(jsonStr, classOfT);
			} else {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(response.code());
				status.setReasonPhrase(response.message());
				throw new IncorrectHttpStatusCodeException("执行链接[" + url + "]的GET请求发生异常", status);
			}
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行链接[" + url + "]的GET请求发生异常", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 检查链接返回字符串是否是微信返回码错误信息
	 * 
	 * @param jsonStr
	 * @param errMessage
	 */
	private static void checkErrCode(String jsonStr, String errMessage) {
		// System.out.println(jsonStr);
		try {
			ReturnCode rc = gson.fromJson(jsonStr, ReturnCode.class);
			// 如果存在errcode并且不为0，说明操作返回了错误码
			if (rc != null && rc.getErrcode() != ReturnCode.SC_OK) {
				// System.out.println("->2");
				throw new IncorrectWeixinReturnCodeException("微信接口返回错误码", rc);
			}
		} catch (JsonSyntaxException e) {
			// 转换ReturnCode出现异常，不处理跳过
			// System.out.println("->3");
		}
		// System.out.println("->4");
	}

	/**
	 * 获取post请求返回的json字符串对应的类型对象
	 * 
	 * @param url
	 *            请求链接地址
	 * @param jsonObj
	 *            post请求体对象
	 * @param classOfT
	 *            返回对象类型
	 * @return
	 */
	public static <T> T post(String url, Object jsonObj, Class<T> classOfT) {
		Request request = new Request.Builder().url(url).addHeader("Accept", "application/json")
				.post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(jsonObj))).build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String jsonStr = response.body().string();
				// 如果返回值类型不是ReturnCode及其子类
				if (!ReturnCode.class.isAssignableFrom(classOfT)) {
					checkErrCode(jsonStr, "执行链接[" + url + "]的POST请求返回微信错误码");
				}
				return gson.fromJson(jsonStr, classOfT);
			} else {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(response.code());
				status.setReasonPhrase(response.message());
				throw new IncorrectHttpStatusCodeException("执行链接[" + url + "]的POST请求发生异常", status);
			}
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行链接[" + url + "]的POST请求发生异常", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 上传文件，返回的json字符串对应的类型对象
	 * 
	 * @param url
	 * @param file
	 * @param fieldName
	 * @param classOfT
	 * @return
	 */
	public static <T> T upload(String url, File file, String mediaFieldName, Class<T> classOfT) {
		return upload(url, file, mediaFieldName, null, null, classOfT);
	}

	/**
	 * 上传文件，返回的json字符串对应的类型对象
	 * 
	 * @param url
	 * @param file
	 * @param mediaFieldName
	 * @param jsonObj
	 * @param jsonFieldName
	 * @param classOfT
	 * @return
	 */
	public static <T> T upload(String url, File file, String mediaFieldName, Object jsonObj, String jsonFieldName,
			Class<T> classOfT) {

		Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(mediaFieldName,
				file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));

		if (jsonFieldName != null && jsonObj != null) {
			builder.addFormDataPart(jsonFieldName, gson.toJson(jsonObj));
		}

		RequestBody muiltipartBody = builder.build();

		Request request = new Request.Builder().url(url).addHeader("Accept", "application/json").post(muiltipartBody)
				.build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String jsonStr = response.body().string();
				// 如果返回值类型不是ReturnCode及其子类
				if (!ReturnCode.class.isAssignableFrom(classOfT)) {
					checkErrCode(jsonStr, "执行文件上传链接[" + url + "]的POST请求返回微信错误码");
				}
				return gson.fromJson(jsonStr, classOfT);
			} else {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(response.code());
				status.setReasonPhrase(response.message());
				throw new IncorrectHttpStatusCodeException("执行文件上传链接[" + url + "]的POST请求发生异常", status);
			}
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行文件上传链接[" + url + "]的POST请求发生异常", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 上传字节数组和json字符串，返回json字符串
	 * 
	 * @param url
	 * @param bytes
	 * @param mediaFieldName
	 * @param filename
	 * @param jsonObj
	 * @param jsonFieldName
	 * @param classOfT
	 * @return
	 */
	public static <T> T upload(String url, byte[] bytes, String mediaFieldName, String filename, Object jsonObj,
			String jsonFieldName, Class<T> classOfT) {

		Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart(mediaFieldName,
				filename, RequestBody.create(MediaType.parse("application/octet-stream"), bytes));

		if (jsonFieldName != null && jsonObj != null) {
			builder.addFormDataPart(jsonFieldName, gson.toJson(jsonObj));
		}

		RequestBody muiltipartBody = builder.build();

		Request request = new Request.Builder().url(url).addHeader("Accept", "application/json").post(muiltipartBody)
				.build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String jsonStr = response.body().string();
				// 如果返回值类型不是ReturnCode及其子类
				if (!ReturnCode.class.isAssignableFrom(classOfT)) {
					checkErrCode(jsonStr, "执行文件上传链接[" + url + "]的POST请求返回微信错误码");
				}
				return gson.fromJson(jsonStr, classOfT);
			} else {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(response.code());
				status.setReasonPhrase(response.message());
				throw new IncorrectHttpStatusCodeException("执行文件上传链接[" + url + "]的POST请求发生异常", status);
			}
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行文件上传链接[" + url + "]的POST请求发生异常", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 上传二进制数组，返回的json字符串对应的类型对象
	 * 
	 * @param url
	 * @param bytes
	 * @param fieldName
	 * @param filename
	 * @param classOfT
	 * @return
	 */
	public static <T> T upload(String url, byte[] bytes, String fieldName, String filename, Class<T> classOfT) {
		return upload(url, bytes, fieldName, filename, null, null, classOfT);
	}

	/**
	 * post请求，提交json数据，返回字节数组
	 * 
	 * @param url
	 * @param jsonObj
	 * @return
	 */
	public static BytesFile download(String url, Object jsonObj) {
		Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_JSON, gson.toJson(jsonObj)))
				.build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				BytesFile bf = new BytesFile();
				String header = response.header("Content-Disposition");

				bf.setFilename(getFilename(header));
				bf.setBytes(response.body().bytes());

				return bf;
			} else {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(response.code());
				status.setReasonPhrase(response.message());
				throw new IncorrectHttpStatusCodeException("执行文件下载链接[" + url + "]的POST请求发生异常", status);
			}
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行文件下载链接[" + url + "]的POST请求发生异常", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * get请求，返回字节文件
	 * 
	 * @param url
	 * @return
	 */
	public static BytesFile download(String url) {
		Request request = new Request.Builder().get().url(url).build();

		Response response = null;
		try {
			response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				BytesFile bf = new BytesFile();
				String header = response.header("Content-Disposition");

				bf.setFilename(getFilename(header));
				bf.setBytes(response.body().bytes());

				return bf;
			} else {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(response.code());
				status.setReasonPhrase(response.message());
				throw new IncorrectHttpStatusCodeException("执行文件下载链接[" + url + "]的GET请求发生异常", status);
			}
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行文件下载链接[" + url + "]的GET请求发生异常", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 将byte数组转换为File
	 * 
	 * @param bytes
	 * @return
	 * @throws WeixinInterfaceException
	 */
	public static File getFile(byte[] bytes, String outputFile) {
		File file = new File(outputFile);
		OutputStream fstream = null;
		BufferedOutputStream stream = null;
		try {
			fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(bytes);
			return file;
		} catch (IOException e) {
			throw new InvalidFileResourceException("从字节数组转换成文件" + outputFile + "失败", e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭BufferedOutputStream失败", e);
				}
			}
			if (fstream != null) {
				try {
					fstream.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭FileOutputStream失败", e);
				}
			}
		}
	}

	/**
	 * 将文件转换为字节数组
	 * 
	 * @param filePath
	 * @return
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			File file = new File(filePath);
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream(BUFFER_SIZE);
			byte[] b = new byte[BUFFER_SIZE];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		} catch (IOException e) {
			throw new InvalidFileResourceException("从文件" + filePath + "转换字节数组失败", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭FileInputStream失败", e);
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭ByteArrayOutputStream失败", e);
				}
			}
		}
		return buffer;
	}

	/**
	 * 获取response header中Content-Disposition中的filename值
	 * 
	 * @param response
	 * @return
	 */
	private static String getFilename(String headerValue) {
		if (StringUtil.isNull(headerValue)) {
			return null;
		}

		String[] elements = headerValue.split(";");

		for (String element : elements) {
			if (!StringUtil.isNull(element)) {
				String[] parameters = element.split("=");
				if (parameters.length >= 2 && "filename".equalsIgnoreCase(parameters[0].trim())) {
					String filename = parameters[1].trim();
					
					// 去除首尾的双引号
					filename = filename.indexOf("\"") == 0 ? filename.substring(1, filename.length()) : filename;
					filename = filename.lastIndexOf("\"") == (filename.length() - 1) ? filename.substring(0, filename.length() - 1) : filename;

					return filename.trim();
				}
			}
		}

		return null;
	}
}
