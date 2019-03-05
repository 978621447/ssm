/**
 * 软件著作权：中国农业银行软件开发中心
 * 
 * 系统名称：互联网金融项目
 * 
 */
package com.wjy.ssm.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;


/**
 * 字符操作工具类
 */
public abstract class StringUtil {
	/**
	 * 根据字符和出现次数生成字符串，如repeat('a',5) -> "aaaaa"
	 * @param chr 指定的字符
	 * @param times 出现次数
	 * @return 目标字符串
	 */
	public static String repeat(char chr, int times) {
		char[] chrs = new char[times];
		Arrays.fill(chrs, chr);
		return new String(chrs);
	}
	
	/**
	 * 判断字符串是否为空或null
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str) || "null".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}	
	
	/**
	 * 方法说明 :根据编码格式，截取特定字节长度
	 * gbk和gb2312都是用两个字节存储一个汉字，而UTF-8是用三个字节存储一个汉字
	 *
	 * @author 宋杰 2014年1月4日下午8:02:09
	 *
	 * @param input
	 * 				输入的字符串
	 * 
	 * @param length
	 * 				要截取的字节的长度，注意是字节不是字符
	 * @param encoding
	 * 				字符串编码
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String subString(String input, int length, String encoding)
			throws UnsupportedEncodingException {
		int characterNum = input.length();
		
		int total = 0;
		
		int len = 0;
		
		for(int i=0; i<characterNum; i++){
			
			String temp = input.substring(i, i+1);
			int temp_len = temp.getBytes(encoding).length;
			
			total += temp_len;
			
			if(total <= length){
				len++;
			}else{
				break;
			}
			
		}
		
		return input.substring(0, len);
	}
	
}
