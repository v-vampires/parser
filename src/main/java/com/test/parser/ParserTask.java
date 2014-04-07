package com.test.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;

import com.test.operator.Business;
import com.test.operator.OperatorInvocation;
import com.test.util.ExcelEntity;
import com.test.util.ExcelUtil;
import com.test.util.HttpClientUtil;

public class ParserTask implements Callable<String> {

	private String type;

	private String typeUrl;

	public ParserTask(String type, String typeUrl) {
		super();
		this.type = type;
		this.typeUrl = typeUrl;
	}

	public String call() throws Exception {
		OperatorInvocation invocation = new OperatorInvocation();
		List<String> urls = getUrls(typeUrl);
		Set<ExcelEntity> bs = new HashSet<ExcelEntity>();
		for (String temp : urls) {
			invocation.init();
			Business b = invocation.proceed(temp);
			if (b != null) {
				bs.add(b);
			}
		}
		createExcel("E:\\lashou\\" + type + "-" + "20130407" + ".xls", bs);
		return null;
	}

	public List<String> getUrls(String url) throws HttpException, IOException {
		// String url =
		// "http://tuan.baidu.com/local/beijing/liren-tg/*/?newest=1#main";
		List<String> urls = new ArrayList<String>(100);
		for (int i = 1; i <= 100; i++) {
			System.out.println("第" + i + "页");
			String tmpUrl = url.replace("*", String.valueOf(i));
			String tmpHtml = HttpClientUtil.getHtml(tmpUrl);
			if (isNotHasTuan(tmpHtml)) {
				break;
			}
			Pattern pattern = Pattern
					.compile("<a class=\"fcb\" mon=\"#outsite=1&content=title\" href=\"(.*?)\"");
			Matcher matcher = pattern.matcher(tmpHtml);
			while (matcher.find()) {

				String result1 = matcher.group(1);
				System.out.println(result1);
				urls.add(result1);
			}
		}
		return urls;
	}

	public boolean isNotHasTuan(String html) {
		Pattern pattern = Pattern
				.compile("<img src=\"/static/widget/common/search-tip/img/tip_text.png\"");
		Matcher matcher = pattern.matcher(html);
		if (matcher.find()) {
			return true;
		}
		return false;
	}
	
	 public void createExcel(String fileName,Set<ExcelEntity> bs) throws Exception
	    {
	        String[] headers = "name|名称,address|地址,phone|电话,source|来源,url|url".split("[,]");
	        // 创建Excel的顶部导航
	        ExcelUtil excel = new ExcelUtil(headers);
	        
	        int index = 0;
	        for(ExcelEntity t : bs)
	        {
	            index++;
	            try
	            {
	                excel.addRow(t, index);
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
	            }
	        }
	        excel.write(fileName);
	    }

}