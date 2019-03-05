package com.wjy.ssm.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 
  * @ClassName(类名)      : HttpUtil
  * @Description(描述)    : http请求调用工具类
  * @author(作者)         ：曹轩
  * @date (开发日期)      ：2016-6-2 上午9:12:44
  *
 */
public class HttpUtil {
	//日志
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	/**
	 * 
	 * @Description(功能描述)    : 发送Post请求（返回字符串信息）   
	 * @author(作者)             ：  曹轩
	 * @date (开发日期)          :  2016-6-2 上午9:13:30 
	 * @param url
	 * @param param
	 * @return  String
	 */
	public static String senPost(String url, String param){
		PrintWriter writer = null;
		URL send_url;
		BufferedReader in = null;
		String result = "";
		try {
			send_url = new URL(url);
			//打开连接
			URLConnection connect =send_url.openConnection();
			//设置请求属性
			connect.setRequestProperty("accept", "*/*");
			connect.setRequestProperty("connection", "Keep-Alive");
			 // 发送POST请求必须设置如下两行
			connect.setDoOutput(true);
			connect.setDoInput(true);
			//设置连接超时，读取超时
			connect.setConnectTimeout(1000*60);
			connect.setReadTimeout(1000*60);
			//创建输出流(UTF-8)
			writer = new PrintWriter(new OutputStreamWriter(connect.getOutputStream(),"UTF-8"));
			writer.print(param);
			writer.flush();
			//定义BufferedReader获取Url响应信息
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String line;
			while((line = in.readLine())!=null){
				result +=line;
			}
		} catch (Exception e) {
			logger.error("post请求出现错误！", e);
			throw new BusinessException("发送POST请求出现异常！");
		}finally{
			 try{
	                if(writer!=null){
	                	writer.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException e){
	            	logger.error("post请求出现错误！", e);
	    			throw new BusinessException("发送POST请求出现异常！");
	            }
		}
		return result;
	}
	
	
	public static String sendPost(String url, String param){
		PrintWriter writer = null;
		URL send_url;
		BufferedReader in = null;
		String result = "";
		try {
			send_url = new URL(url);
			//打开连接
			URLConnection connect =send_url.openConnection();
			//设置请求属性
			connect.setRequestProperty("accept", "*/*");
			connect.setRequestProperty("connection", "Keep-Alive");
			connect.setRequestProperty("content-type", "text/html");
			 // 发送POST请求必须设置如下两行
			connect.setDoOutput(true);
			connect.setDoInput(true);
			//设置连接超时，读取超时
			connect.setConnectTimeout(1000*60);
			connect.setReadTimeout(1000*60);
			//创建输出流(UTF-8)
			writer = new PrintWriter(new OutputStreamWriter(connect.getOutputStream(),"UTF-8"));
			writer.print(param);
			writer.flush();
			//定义BufferedReader获取Url响应信息
			in = new BufferedReader(new InputStreamReader(connect.getInputStream(),"UTF-8"));
			String line;
			while((line = in.readLine())!=null){
				result +=line;
			}
		} catch (Exception e) {
			logger.error("post请求出现错误！", e);
			throw new BusinessException("发送POST请求出现异常！");
		}finally{
			 try{
	                if(writer!=null){
	                	writer.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException e){
	            	logger.error("post请求出现错误！", e);
	    			throw new BusinessException("发送POST请求出现异常！");
	            }
		}
		return result;
	}


	public static String getBodyMessage(HttpServletRequest request) {

		String line = "";
		String xmlString = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));

			StringBuffer inputString = new StringBuffer();

			while ((line = reader.readLine()) != null) {
				inputString.append(line);
				System.out.println("11111111111111111111:"+inputString);
			}
			xmlString = inputString.toString();
			reader.close();

		}catch (IOException e){

		}
		return xmlString;
	}
	public static String httpURLConnection(String url, String param) {
	    PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)"); 
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(1000*60);
            conn.setReadTimeout(1000*60);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
			throw new BusinessException("发送 POST 请求出现异常！"+e);
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
   }




	public static String sendPostJson(String url,String param){
		PrintWriter writer = null;
		URL send_url;
		BufferedReader in = null;
		String result = "";
		try {
			send_url = new URL(url);
			//打开连接
			URLConnection connect =send_url.openConnection();
			//设置请求属性
			connect.setRequestProperty("accept", "*/*");
			connect.setRequestProperty("connection", "Keep-Alive");
			connect.setRequestProperty("content-type", "application/json");
			// 发送POST请求必须设置如下两行
			connect.setDoOutput(true);
			connect.setDoInput(true);
			//设置连接超时，读取超时
			connect.setConnectTimeout(1000*60);
			connect.setReadTimeout(1000*60);
			//创建输出流(UTF-8)
			writer = new PrintWriter(new OutputStreamWriter(connect.getOutputStream(),"UTF-8"));
			writer.print(param);
			writer.flush();
			//定义BufferedReader获取Url响应信息
			in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String line;
			while((line = in.readLine())!=null){
				result +=line;
			}
		} catch (Exception e) {
			e.printStackTrace();
			//logger.error("post请求出现错误！", e);
			throw new BusinessException("发送POST请求出现异常！");
		}finally{
			try{
				if(writer!=null){
					writer.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException e){
				//logger.error("post请求出现错误！", e);
				throw new BusinessException("发送POST请求出现异常！");
			}
		}
		return result;
	}


/**
 * 执行HttpPost请求
 * @param url 请求地址
 * @param params 请求参数
 * @return 结果
 */
	/**
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String sendFivePost(String url, Map<String, String> params, Map<String, String> headerMap)
			throws Exception{
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {

			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			if (headerMap != null) {
				for (Map.Entry entry : headerMap.entrySet()) {
					conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
				}
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(getSignCheckContent(params));
			out.flush();

			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line="";
			while ((line = in.readLine()) != null){
				result = result + line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
			throw e;
		}finally{
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	/**
	 * @param params
	 * @return
	 */
	public static String getSignCheckContent(Map<String, String> params){
		if ((params == null) || (params.isEmpty())) {
			return "";
		}
		StringBuffer content = new StringBuffer();
		try{
			List keys = new ArrayList(params.keySet());
			Collections.sort(keys);
			for (int i = 0; i < keys.size(); ++i) {
				String key = keys.get(i).toString();
				String value = (String) params.get(key);
				value = (HttpUtil.isNullOrEmpty(value)) ? "" : value;
				content.append(((i == 0) ? "" : "&") + key + "=" + value);
			}
		}catch (Exception e){
			logger.info("getSignCheckContent方法调用出现异常！" + e);
			e.printStackTrace();
		}
		return content.toString();
	}

	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof CharSequence) {
			return ((CharSequence) obj).length() == 0;
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}

	public static String doPost(String url,JSONObject jsonParamter) {

		String jsonParamterStr = jsonParamter.toString();

		//调用http接口
		String  resultJsonStr = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(jsonParamterStr, "UTF-8");
			entity.setContentType("application/json;charset=UTF-8");
			httpPost.setEntity(entity);
			HttpClient client = HttpClients.createDefault();;
			HttpResponse response = client.execute(httpPost);
			resultJsonStr = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return  resultJsonStr;

	}

}
