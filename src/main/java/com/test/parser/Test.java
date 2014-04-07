package com.test.parser;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class Test
{
    
    /**
     * @param args
     * @throws IOException
     * @throws HttpException
     */
    public static void main(String[] args) throws HttpException, IOException
    {
        String url = "http://tuan.baidu.com/forward?url=http%3A%2F%2Fcps.dianping.com%2Fredirect%2Fhao123%3Furl%3Dhttp%253A%252F%252Ft.dianping.com%252Fdeal%252F2257733%26tn%3Dbaidutuan_tg%26baiduid%3D3e939c72c804e8aef5e140298abf0ed9&salt=0ece643c922a0b8f325b117f01d05d51";
        // String url = "http://tuan.baidu.com/forward?url=http%3A%2F%2Fr.union.meituan.com%2Fcps%2Fbdt%3Furl%3Dhttp%3A%2F%2Fwww.meituan.com%2Fdeal%2F18597004.html%26amp%3Bcity%3D692e92669c0ca340eff4fdcef32896ee%26tn%3Dbaidutuan_tg%26baiduid%3D730838d6aadf017abf5a821d6cb66310&amp;salt=11ab1fd4dc7834027bd245782bd01187";
        // String url = "http://tuan.baidu.com/forward?url=http%3A%2F%2Fr.union.meituan.com%2Fcps%2Fbdt%3Furl%3Dhttp%3A%2F%2Fwww.meituan.com%2Fdeal%2F18597004.html%26amp%3Bcity%3D692e92669c0ca340eff4fdcef32896ee%26tn%3Dbaidutuan_tg%26baiduid%3D730838d6aadf017abf5a821d6cb66310&salt=11ab1fd4dc7834027bd245782bd01187";
        // String url = "http://tuan.baidu.com/forward?url=http%3A%2F%2Fwww.go.cn%2Findex.php%3Fm%3Ddeal%26amp%3Bact%3Dshow%26amp%3Bgid%3D395803%26amp%3Bfrom%3Dtuanbaidu%26tn%3Dbaidutuan_tg%26baiduid%3D730838d6aadf017abf5a821d6cb66310&salt=329b57bbe000fe3bd746d626c2277fd9";
        String phone_url = "http://t.dianping.com/ajax/dealGroupShopDetail?dealGroupId=2191424&cityId=2&action=shops&regionId=0&page=1";
        String t_url = "http%3A%2F%2Ftuan.baidu.com%2Fforward%3Furl%3Dhttp%253A%252F%252Fcps.dianping.com%252Fredirect%252Fhao123%253Furl%253Dhttp%25253A%25252F%25252Ft.dianping.com%25252Fdeal%25252F2260676%2526tn%253Dbaidutuan_tg%2526baiduid%253Debdd9ebced12fb06b7c873d2a8db2ec0%26salt%3Da7d52fd6745e9f622cd15d82c0a94b19";
        System.out.println(URLDecoder.decode(URLDecoder.decode(URLDecoder.decode(t_url))));
        String html = getHtml(phone_url);
        System.out.println(html);
        System.out.println(getName(html));
        System.out.println(getHtml(phone_url));
        System.out.println("---------------");
    }
    
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
    
    public static String getName(String html)
    {
        // System.out.println(html);
        // Pattern pattern = Pattern.compile("<div class=\"deal-title\">.*<h1>.*[.*].*(.*).*</h1>");
        Pattern pattern = Pattern.compile("shortTitle:'(.*)'");
        Matcher matcher = pattern.matcher(html);
        while(matcher.find())
        {
            String result1 = matcher.group(1);
            return result1;
        }
        return null;
    }
}
