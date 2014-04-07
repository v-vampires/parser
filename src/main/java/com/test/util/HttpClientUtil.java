package com.test.util;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpClientUtil
{
    public static String getHtml(String url) throws HttpException, IOException
    {
        HttpClient client = new HttpClient();
        client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
        client.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.52 Safari/537.17");
        // 设置代理服务器地址和端口
        // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        // 使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
        HttpMethod method = new GetMethod(url);
        // 使用POST方法
        // HttpMethod method = new PostMethod("http://java.sun.com");
        client.executeMethod(method);
        // 打印服务器返回的状态
        System.out.println(method.getStatusLine());
        // 打印返回的信息
        String html = method.getResponseBodyAsString();
        // 释放连接
        method.releaseConnection();
        return html;
    }
}
