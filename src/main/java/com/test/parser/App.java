package com.test.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpException;

import com.test.operator.Business;
import com.test.operator.OperatorInvocation;
import com.test.util.ExcelEntity;
import com.test.util.ExcelUtil;
import com.test.util.HttpClientUtil;

/**
 * Hello world!
 * 
 */
public class App
{
	
    public static void main(String[] args) throws Exception
    {
    	ExecutorService pool = Executors.newFixedThreadPool(6);
        Map<String,String> map = getTypeUrls();
        for(Entry<String, String>  e: map.entrySet()){
        	ParserTask task = new ParserTask(e.getKey(), e.getValue());
        	pool.submit(task);
        }
        pool.shutdown();
    }
    
    public static Map<String,String> getTypeUrls(){
    	Map<String,String> map = new HashMap<String, String>();
    	map.put("美食", "http://tuan.baidu.com/local/beijing/canyinmeishi-tg/*/?newest=1#main");
    	//map.put("电影", "http://tuan.baidu.com/movie?newest=1#goods-hd-wrap");
    	map.put("娱乐", "http://tuan.baidu.com/local/beijing/xiuxianyule-tg/*/?newest=1#main");
    	map.put("酒店", "http://tuan.baidu.com/local/beijing/jiudian-tg/*/?hotel_city=beijing&newest=1");
    	map.put("旅游", "http://tuan.baidu.com/local/beijing/jiudianlvyou-tg/*/?newest=1#main");
    	map.put("生活", "http://tuan.baidu.com/local/beijing/shenghuofuwu-tg/*/?newest=1#main");
    	map.put("丽人", "http://tuan.baidu.com/local/beijing/liren-tg/*/?newest=1#main");
    	return map;
    }
    
    
}
