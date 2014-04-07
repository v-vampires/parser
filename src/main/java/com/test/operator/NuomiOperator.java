package com.test.operator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuomiOperator extends AbstractTuanOperator
{
    private static final String F = ".nuomi.";
    
    private static final String SOURCE = "百度糯米";
    
    public Business parse(String url, OperatorInvocation oi) throws Exception
    {
        if(url.indexOf(F) > -1)
        {
        	String html = getHtml(url);
        	String name = getTuanGouName(html);
        	if(name!=null){
        		if(isNotExistInLashou(name)){
        			String address = null;
        			String phone = null;
        			Pattern p = Pattern.compile("title=\"地址：(.*?)\"");
        			Matcher m = p.matcher(html);
        			while(m.find()){
        				address = m.group(1);
        			}
        			p = Pattern.compile("<p class=\"limit1\">电话：(.*?)</p>");
        			m = p.matcher(html);
        			while(m.find()){
        				phone = m.group(1);
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
    
    public String getTuanGouName(String html){
    	Pattern p = Pattern.compile("<span class=\"goodCurr\">(.*?)团购</span>");
    	Matcher m = p.matcher(html);
    	while(m.find()){
    		return m.group(1);
    	}
    	return null;
    }
}
