package com.test.parser;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.test.util.HttpClientUtil;

public class NiomiTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		String u1 ="http://bj.nuomi.com/deal/hezqiu7g.html?utm_source=tuanbaidu&utm_medium=tp_api&cid=006003&tn=baidutuan_tg&baiduid=e45600568e6b64489afa8d5f6380d227&from=pc_site";
		System.out.println(HttpClientUtil.getHtml(u1));
	}

}
