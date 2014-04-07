package com.test.operator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeituanOperator extends AbstractTuanOperator
{
    private static final String F = ".meituan.";
    
    private static final String SOURCE = "美团网";
    
    public Business parse(String url, OperatorInvocation oi) throws Exception
    {
        if(url.indexOf(F) > -1)
        {
            String html = getHtml(url);
            Pattern pattern = Pattern.compile("<h1 class=\"deal-component-title\">(.*)</h1>");
            Matcher matcher = pattern.matcher(html);
            String name = null;
            while(matcher.find())
            {
                name = matcher.group(1);
            }
            if(name == null)
            {
                // 记录该url没找到name
            }
            else
            {
                if(isNotExistInLashou(name)){
                	
                     Pattern pattern1 = Pattern.compile("\"address\":\"(.*?)\"");
                     Matcher matcher1 = pattern1.matcher(html);
                     String address = null;
                     if(matcher1.find())
                     {
                    	 address = unicodeToGB(matcher1.group(1));
                     }
                     pattern1 = Pattern.compile("\"phone\":\"(.*?)\"");
                     matcher1 = pattern1.matcher(html);
                     String phone = null;
                     if(matcher1.find())
                     {
                    	 phone = matcher1.group(1);
                     }
                     Business b = new Business();
                     b.setSource(SOURCE);
                     b.setUrl(url);
                     b.setName(name);
                     b.setAddress(address);
                     b.setPhone(phone);
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
    
}
