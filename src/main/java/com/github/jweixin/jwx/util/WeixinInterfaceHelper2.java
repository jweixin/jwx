package com.github.jweixin.jwx.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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

/**
 * 微信接口工具类
 * 封装了通过http协议获取微信服务的结果
 * @author Administrator
 *
 */
public class WeixinInterfaceHelper2 {

	/**
	 * 设置json命名策略，java对象域驼峰格式，json字符串是小写下划线格式
	 */
	private static Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(AbstractButton.class, new AbstractButtonAdapter())
			.registerTypeAdapter(Button.class, new ButtonAdapter())
			.registerTypeAdapter(AbstractMenu.class, new AbstractMenuAdapter())
			.create();
	
	/**
	 * 缓存大小
	 */
	private final static int BUFFER_SIZE = 4096;

	/**
	 * 获取get请求返回的json字符串对应的类型对象
	 * @param url
	 * @param classOfT
	 * @return
	 */
	public static <T> T get(String url, Class<T> classOfT) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.addHeader("Content-Type", "application/json; charset=utf-8");
		get.setHeader("Accept", "application/json");
		
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(get);
			
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(statusCode);
				status.setReasonPhrase(sl.getReasonPhrase());
				throw new IncorrectHttpStatusCodeException("执行链接[" + url + "]的GET请求发生异常", status);
			}		
			
			String jsonStr = null;
			try {
				jsonStr = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
			} catch (UnsupportedOperationException | IOException e) {
				throw new HttpAccessFailureException("获取链接[" + url + "]的GET请求结果发生异常", e);
			}
			// 如果返回值类型不是ReturnCode及其子类
			if (!ReturnCode.class.isAssignableFrom(classOfT)) {
				checkErrCode(jsonStr, "执行链接[" + url + "]的GET请求返回微信错误码");
			}
			
			return gson.fromJson(jsonStr, classOfT);
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行链接[" + url + "]的GET请求发生异常", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭http的GET请求响应失败", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭httpClient对象失败", e);
				}
			}
		}
	}
	
	/**
	 * 检查链接返回字符串是否是微信返回码错误信息
	 * @param jsonStr
	 * @param errMessage
	 */
	private static void checkErrCode(String jsonStr, String errMessage) {
		//System.out.println(jsonStr);
		try{
			ReturnCode rc = gson.fromJson(jsonStr, ReturnCode.class);
			//如果存在errcode并且不为0，说明操作返回了错误码
			if(rc!=null && rc.getErrcode()!=ReturnCode.SC_OK){
				//System.out.println("->2");
				throw new IncorrectWeixinReturnCodeException("微信接口返回错误码", rc);
			}
		} catch(JsonSyntaxException e){
			//转换ReturnCode出现异常，不处理跳过
			//System.out.println("->3");
		}
		//System.out.println("->4");
	}
	
	/**
	 * 获取post请求返回的json字符串对应的类型对象
	 * @param url 请求链接地址
	 * @param jsonObj post请求体对象
	 * @param classOfT 返回对象类型
	 * @return
	 */
	public static <T> T post(String url, Object jsonObj, Class<T> classOfT) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/json; charset=utf-8");
		post.setHeader("Accept", "application/json");
		post.setEntity(new StringEntity(gson.toJson(jsonObj), Charset.forName("UTF-8")));
		
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(post);
			
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(statusCode);
				status.setReasonPhrase(sl.getReasonPhrase());
				throw new IncorrectHttpStatusCodeException("执行链接[" + url + "]的POST请求发生异常", status);
			}		
			
			String jsonStr = null;
			try {
				jsonStr = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
			} catch (UnsupportedOperationException | IOException e) {
				throw new HttpAccessFailureException("获取链接[" + url + "]的POST请求结果发生异常", e);
			}
			// 如果返回值类型不是ReturnCode及其子类
			if (!ReturnCode.class.isAssignableFrom(classOfT)) {
				checkErrCode(jsonStr, "执行链接[" + url + "]的POST请求返回微信错误码");
			}
			
			return gson.fromJson(jsonStr, classOfT);
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行链接[" + url + "]的POST请求发生异常", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭http的POST请求响应失败", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭httpClient对象失败", e);
				}
			}
		}
	}

	/**
	 * 上传文件，返回的json字符串对应的类型对象
	 * @param url
	 * @param file
	 * @param fieldName
	 * @param classOfT
	 * @return
	 */
	public static <T> T upload(String url, File file, String fieldName, Class<T> classOfT) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "application/json");
		FileBody fb = new FileBody(file);

		HttpEntity httpEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addPart(fieldName, fb).build();
		post.setEntity(httpEntity);
		
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(post);

			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(statusCode);
				status.setReasonPhrase(sl.getReasonPhrase());
				throw new IncorrectHttpStatusCodeException("执行文件上传链接[" + url + "]的POST请求发生异常", status);
			}		
			
			String jsonStr = null;
			try {
				jsonStr = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
			} catch (UnsupportedOperationException | IOException e) {
				throw new HttpAccessFailureException("获取文件上传链接[" + url + "]的POST请求结果发生异常", e);
			}
			// 如果返回值类型不是ReturnCode及其子类
			if (!ReturnCode.class.isAssignableFrom(classOfT)) {
				checkErrCode(jsonStr, "执行文件上传链接[" + url + "]的POST请求返回微信错误码");
			}
			
			return gson.fromJson(jsonStr, classOfT);
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行文件上传链接[" + url + "]的POST请求发生异常", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭http的POST请求响应失败", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭httpClient对象失败", e);
				}
			}
		}
	}

	/**
	 * 上传字节数组和json字符串，返回json字符串
	 * @param url
	 * @param bytes
	 * @param mediaFieldName
	 * @param filename
	 * @param jsonObj
	 * @param jsonFieldName
	 * @param classOfT
	 * @return
	 */
	public static <T> T upload(String url, byte[] bytes, String mediaFieldName, String filename,
			Object jsonObj, String jsonFieldName, Class<T> classOfT) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("Accept", "application/json");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addBinaryBody(mediaFieldName, bytes, ContentType.MULTIPART_FORM_DATA, filename);
		if (jsonObj != null && jsonFieldName != null) {
			builder.addTextBody(jsonFieldName, gson.toJson(jsonObj),
					ContentType.create("application/json", Charset.forName("UTF-8")));
		}

		HttpEntity httpEntity = builder.build();
		post.setEntity(httpEntity);

		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(post);
			
			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				HttpReturnStatus status = new HttpReturnStatus();
				status.setStatusCode(statusCode);
				status.setReasonPhrase(sl.getReasonPhrase());
				throw new IncorrectHttpStatusCodeException("执行文件上传链接[" + url + "]的POST请求发生异常", status);
			}		
			
			String jsonStr = null;
			try {
				jsonStr = IOUtils.toString(response.getEntity().getContent(), Charset.forName("UTF-8"));
			} catch (UnsupportedOperationException | IOException e) {
				throw new HttpAccessFailureException("获取文件上传链接[" + url + "]的POST请求结果发生异常", e);
			}
			// 如果返回值类型不是ReturnCode及其子类
			if (!ReturnCode.class.isAssignableFrom(classOfT)) {
				checkErrCode(jsonStr, "执行文件上传链接[" + url + "]的POST请求返回微信错误码");
			}
			
			return gson.fromJson(jsonStr, classOfT);
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行文件上传链接[" + url + "]的POST文件上传请求发生异常", e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭http的POST请求响应失败", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭httpClient对象失败", e);
				}
			}
		}
	}

	/**
	 * 上传二进制数组，返回的json字符串对应的类型对象
	 * @param url
	 * @param bytes
	 * @param fieldName
	 * @param filename
	 * @param classOfT
	 * @return
	 */
	public static <T> T upload(String url, byte[] bytes, String fieldName, String filename,
			Class<T> classOfT) {
		return upload(url, bytes, fieldName, filename, null, null, classOfT);
	}

	/**
	 * post请求，提交json数据，返回字节数组
	 * @param url
	 * @param jsonObj
	 * @return
	 */
	public static BytesFile download(String url, Object jsonObj) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/json; charset=utf-8");
		post.setEntity(new StringEntity(gson.toJson(jsonObj), Charset.forName("UTF-8")));

		CloseableHttpResponse response = null;
		InputStream in = null;
		ByteArrayOutputStream outStream = null;

		try {
			response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			
			BytesFile bf = new BytesFile();
			Header contentHeader = response.getFirstHeader("Content-Disposition");
			String filename = null;
			if (contentHeader != null) {
				HeaderElement[] values = contentHeader.getElements();
				if (values.length == 1) {
					NameValuePair param = values[0].getParameterByName("filename");
					if (param != null) {
						try {
							// filename = new
							// String(param.getValue().toString().getBytes(),
							// "utf-8");
							// filename=URLDecoder.decode(param.getValue(),"utf-8");
							filename = param.getValue();
							bf.setFilename(filename);
						} catch (Exception e) {
							bf.setFilename(null);
						}
					}
				}
			}
			
			in = entity.getContent();
			outStream = new ByteArrayOutputStream();
			byte[] data = new byte[BUFFER_SIZE];
			int count = -1;
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
			data = null;
			bf.setBytes(outStream.toByteArray());
			return bf;
		} catch (IOException e) {
			throw new HttpAccessFailureException("执行文件下载链接[" + url + "]的POST请求发生异常", e);
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭ByteArrayOutputStream失败", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭InputStream失败", e);
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭http的POST请求响应失败", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭httpClient对象失败", e);
				}
			}
		}
	}

	/**
	 * get请求，返回字节数组
	 * @param url
	 * @return
	 */
	public static BytesFile download(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.addHeader("Content-Type", "application/json; charset=utf-8");

		CloseableHttpResponse response = null;
		InputStream in = null;
		ByteArrayOutputStream outStream = null;
		try {
			response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity == null) {
				return null;
			}
			
			BytesFile bf = new BytesFile();
			Header contentHeader = response.getFirstHeader("Content-Disposition");
			String filename = null;
			if (contentHeader != null) {
				HeaderElement[] values = contentHeader.getElements();
				if (values.length == 1) {
					NameValuePair param = values[0].getParameterByName("filename");
					if (param != null) {
						try {
							// filename = new
							// String(param.getValue().toString().getBytes(),
							// "utf-8");
							// filename=URLDecoder.decode(param.getValue(),"utf-8");
							filename = param.getValue();
							bf.setFilename(filename);
						} catch (Exception e) {
							bf.setFilename(null);
						}
					}
				}
			}
			
			in = entity.getContent();
			outStream = new ByteArrayOutputStream();
			byte[] data = new byte[BUFFER_SIZE];
			int count = -1;
			while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
				outStream.write(data, 0, count);
			data = null;
			
			bf.setBytes(outStream.toByteArray());
			return bf;
		} catch (Exception e) {
			throw new HttpAccessFailureException("执行文件下载链接[" + url + "]的GET请求发生异常", e);
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭ByteArrayOutputStream失败", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭InputStream失败", e);
				}
			}
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭http的POST请求响应失败", e);
				}
			}
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					throw new CloseableResourceFailureException("关闭httpClient对象失败", e);
				}
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
	 * @param filePath
	 * @return
	 */
	public static byte[] getBytes(String filePath){
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
	public static String getFileName(CloseableHttpResponse response) {
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						// filename = new
						// String(param.getValue().toString().getBytes(),
						// "utf-8");
						// filename=URLDecoder.decode(param.getValue(),"utf-8");
						filename = param.getValue();
					} catch (Exception e) {
						return null;
					}
				}
			}
		}
		return filename;
	}
}
