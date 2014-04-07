package com.test.operator;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DianpingOperator extends AbstractTuanOperator
{
    private static final String F = ".dianping.";
    
    private static final String SOURCE = "大众点评";
    
    public Business parse(String url, OperatorInvocation oi) throws Exception
    {
        if(url.indexOf(F) > -1)
        {
        	url = URLDecoder.decode(URLDecoder.decode(url));
    		Pattern p = Pattern.compile("http://t.dianping.com/deal/([\\d]+)");
    		Matcher m = p.matcher(url);
    		String id = null;
    		while(m.find()){
    			id = m.group(1);
    		}
    		if(id!=null){
    			url = "http://t.dianping.com/deal/"+id;
    			String html = getHtml(url);
    			p = Pattern.compile("shortTitle:'(.*)'");
    			m = p.matcher(html);
    			String name = null;
    			if(m.find()){
    				name = m.group(1);
    			}
    			if(isNotExistInLashou(name)){
    				String detail_url = "http://t.dianping.com/ajax/dealGroupShopDetail?dealGroupId="+id+"&cityId=2&action=shops&regionId=0&page=1";
    				String detailHtml = getHtml(detail_url);
    				p = Pattern.compile("\"address\":\"(.*?)\",.*\"contactPhone\":\"(.*?)\",");
    				m = p.matcher(detailHtml);
    				String address = null;
    				String phone = null;
    				if(m.find()){
    					address = m.group(1);
    					phone = m.group(2);
    				}
    				if(address==null){
    					p = Pattern.compile("<p class=\"dz\">(.*?)</p>");
    					m = p.matcher(html);
    					if(m.find()){
    						address = m.group(1);
    					}
    				}
    				if(phone==null){
    					p = Pattern.compile("预约电话：([\\d]{11}|[\\d-]*)");
    					m = p.matcher(html);
    					if(m.find()){
    						phone = m.group(1);
    					}
    				}
    				Business b = new Business(name, address, phone, url, SOURCE);
    				return b;
    			}
    		}
    		return null;
        }
        else
        {
            return oi.proceed(url);
        }
    }
    
    public static void main(String[] args) throws Exception {
    	//new DianpingOperator().parse("http://t.dianping.com/deal/4004239", new OperatorInvocation());
    	Pattern p =Pattern.compile("预约电话：([\\d-]*)");
    	Matcher m = p.matcher("2014年06月25日(周一-周四不适用)预约电话：010-83568888-2811(09：00-18：00)");
    	while(m.find()){
    		System.out.println(m.group(1));
    	}
	}
}
