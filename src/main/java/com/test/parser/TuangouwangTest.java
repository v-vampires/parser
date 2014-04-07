package com.test.parser;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpException;

import com.test.util.HttpClientUtil;

public class TuangouwangTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		String u1 ="http://www.go.cn/index.php?m=deal&act=show&gid=424904";
		System.out.println(URLDecoder.decode(URLDecoder.decode(u1)));
		//http://www.go.cn/index.php?m=deal&act=show&gid=424904
		System.out.println(HttpClientUtil.getHtml(u1));
	}

}
