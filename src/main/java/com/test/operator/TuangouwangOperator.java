package com.test.operator;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TuangouwangOperator extends AbstractTuanOperator
{
    private static final String F = ".go.";
    
    private static final String SOURCE = "团购王";
    
    public Business parse(String url, OperatorInvocation oi) throws Exception
    {
        if(url.indexOf(F) > -1)
        {
        	String u1 = URLDecoder.decode(URLDecoder.decode(url));
        	Pattern p = Pattern.compile("gid=([\\d]+?)&");
        	Matcher m = p.matcher(u1);
        	String gid = null;
        	while(m.find()){
        		gid = m.group(1);
        	}
        	String t_url = "http://www.go.cn/index.php?m=deal&act=show&gid="+gid;
        	String html = getHtml(t_url);
        	String name = getTuanGouName(html);
        	if(name!=null){
        		if(isNotExistInLashou(name)){
        			String address = null;
        			String phone = null;
        			p = Pattern.compile("\"address\":\"(.*?)\"");
        			m = p.matcher(html);
        			while(m.find()){
        				address = m.group(1);
        			}
        			p = Pattern.compile("\"phone\":\"(.*?)\"");
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
    	Pattern p = Pattern.compile("\"label\":\"(.*?)\"");
    	Matcher m = p.matcher(html);
    	while(m.find()){
    		return m.group(1);
    	}
    	return null;
    }
    
    public static void main(String[] args) throws Exception {
		String url ="http://tuan.baidu.com/forward?url=http%3A%2F%2Fwww.go.cn%2Findex.php%3Fm%3Ddeal%26amp%3Bact%3Dshow%26amp%3Bgid%3D424904%26amp%3Bfrom%3Dtuanbaidu%26tn%3Dbaidutuan_tg%26baiduid%3D7360b28129beee9ff896f70c3f1f06ab&salt=17ba63da63ae2908bf62e105d4f282b1";
		new TuangouwangOperator().parse(url, new OperatorInvocation());
    }
}
