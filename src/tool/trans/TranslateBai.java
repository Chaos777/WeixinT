package tool.trans;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import sign.Encode;
import util.WeixinUtil;

import net.sf.json.JSONObject;

public class TranslateBai {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		translateBai("中国足球");
	}
	
	private static final String URL = "http://api.fanyi.baidu.com/api/trans/vip/translate?";
	private static final String APPID = "20151124000006348";
	private static final String KEY = "qthmNg7IuKHtjtXsIZa4";
	private static final long RANDOM = new Random().nextLong();
	
	
	public static String translateBai(String query){
		StringBuffer sb = new StringBuffer();
		
		String str1 = APPID+query+RANDOM+KEY;
		
//		System.out.println(str1);
		
		String sign = Encode.MD5(str1).toLowerCase();	
		
		System.out.println(sign);
		
		StringBuffer surl = new StringBuffer(URL);
		surl.append("q="+query);
		surl.append("&appid="+APPID);
		surl.append("&salt="+RANDOM);
		surl.append("&from=auto&to=auto&sign="+sign);
		
		System.out.println(surl);
		
		JSONObject jsonObject = WeixinUtil.doGetStr(surl.toString());
		
		
		/*if(jsonObject.getString("error_code") == null || !"52000".equals(jsonObject.getString("error_code"))){
			sb.append("翻译失败");
		}
		else{	*/			//success
		Back_info back_info = (Back_info) JSONObject.toBean(jsonObject,Back_info.class);
			sb.append("翻译结果为:");
			sb.append("原文："+back_info.getTrans_result().getSrc()+"\n");
			sb.append("译文："+back_info.getTrans_result().getDst()+"\n");
//		}
		
		System.out.println(jsonObject);
		
		return sb.toString();
	}
	
	
	
	

}

class Back_info{
	private String from;
	private String to;
	private trans_result trans_result;
	
	public String getfrom() {
		return from;
	}
	public void setfrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public trans_result getTrans_result() {
		return trans_result;
	}
	public void setTrans_result(trans_result trans_result) {
		this.trans_result = trans_result;
	}
	
}

class trans_result{
	private String src;
	private String dst;
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDst() {
		return dst;
	}
	public void setDst(String dst) {
		this.dst = dst;
	}
	
	
}


