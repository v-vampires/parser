package com.test.parser;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.test.util.HttpClientUtil;

public class MeituanTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		String u1 = "http://tuan.baidu.com/forward?url=http%3A%2F%2Fr.union.meituan.com%2Fcps%2Fbdt%3Furl%3Dhttp%3A%2F%2Fbj.meituan.com%2Fdeal%2F19459838.html%26tn%3Dbaidutuan_tg%26baiduid%3D5301b38d2d5566c5eeebcad21bdc6cd7&salt=4b202b7fec2fc3a193f7ba9ddda369a5";
		String h1 = HttpClientUtil.getHtml(u1);
		System.out.println(h1);
	}

}
