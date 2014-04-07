package com.test.operator;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public abstract class AbstractTuanOperator implements ITuanOperator
{
    
    public String getHtml(String url) throws HttpException, IOException
    {
        HttpClient client = new HttpClient();
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
        client.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.52 Safari/537.17");
        // 设置代理服务器地址和端口
        // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        // 使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
        HttpMethod method = new GetMethod(url);
        // 使用POST方法
        // HttpMethod method = new PostMethod("http://java.sun.com");
        client.executeMethod(method);
        // 打印服务器返回的状态
        // System.out.println(method.getStatusLine());
        // 打印返回的信息
        String html = method.getResponseBodyAsString();
        // 释放连接
        method.releaseConnection();
        return html;
    }
    
    public boolean isNotExistInLashou(String name) throws HttpException, IOException{
		// 判断在拉手网是否存在
        String lashouSoUrl = "http://www.lashou.com/search.php?sw=" + URLEncoder.encode(name);
        String lashouHtml = getHtml(lashouSoUrl);
        Pattern p= Pattern.compile("<em>没有找到<b>“" + name + "”</b>相关的团购</em>");
        Matcher  m = p.matcher(lashouHtml);
        if(m.find()){
        	return true;
        }
        return false;
	}
    public String unicodeToGB(String s)
    {
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s, "\\u");
        while(st.hasMoreTokens())
        {
            String t = st.nextToken();
            if(t.length() > 4)
            {
                sb.append((char) Integer.parseInt(t.substring(0, 4), 16)).append(t.substring(4, t.length()));
            }
            else
            {
                sb.append((char) Integer.parseInt(t, 16));
            }
        }
        return sb.toString();
    }
}
