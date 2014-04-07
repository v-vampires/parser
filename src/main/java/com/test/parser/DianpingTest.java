package com.test.parser;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.test.operator.Business;

public class DianpingTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static void main(String[] args) throws HttpException, IOException {
		String url = "http://tuan.baidu.com/forward?url=http://cps.dianping.com/redirect/hao123?url=http://t.dianping.com/deal/2217708&tn=baidutuan_tg&baiduid=6085643996e095c37569e6a41c7208c5&salt=52463cb3e6572eb961b32342e8af860a";
		//http://tuan.baidu.com/forward?url=http://cps.dianping.com/redirect/hao123?url=http://t.dianping.com/deal/2257733&tn=baidutuan_tg&baiduid=3e939c72c804e8aef5e140298abf0ed9&salt=0ece643c922a0b8f325b117f01d05d51
		url = URLDecoder.decode(URLDecoder.decode(url));
		Pattern p = Pattern.compile("http://t.dianping.com/deal/([\\d]+)&");
		Matcher m = p.matcher(url);
		String id = null;
		while(m.find()){
			id = m.group(1);
		}
		if(id!=null){
			String html = getHtml(url);
			p = Pattern.compile("shortTitle:'(.*?)'");
			m = p.matcher(html);
			String name = null;
			if(m.find()){
				name = m.group(1);
			}
			if(isNotExistInLashou(name)){
				String detail_url = "http://t.dianping.com/ajax/dealGroupShopDetail?dealGroupId="+id+"&cityId=2&action=shops&regionId=0&page=1";
				String detailHtml = getHtml(detail_url);
				//{"address":"朝阳区青年路朝阳大悦城公寓2号楼701","avgPrice":null,"branchName":"","businessHours":"9:30-20:00","cityId":0,"contactPhone":"010-85564097 18612105522","dealGroupId":2191424,"glat":39.923871,"glng":116.517741,"regionId":0,"shopId":17655660,"shopName":"华洋国际祛痘祛斑除疤中心","shopPower":null,"voteTotal":null}],"pages":1}
				p = Pattern.compile("\"address\":\"(.*?)\",.*\"contactPhone\":\"(.*?)\",");
				m = p.matcher(detailHtml);
				String address = null;
				String phone = null;
				if(m.find()){
					address = m.group(1);
					phone = m.group(2);
				}
				Business b = new Business(name, address, phone, url, "大众点评");
				System.out.println(b);
			}
			
			
		}
	}

	public static String getHtml(String url) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
		client.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.52 Safari/537.17");

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
	
	public static boolean isNotExistInLashou(String name) throws HttpException, IOException{
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
	
}
