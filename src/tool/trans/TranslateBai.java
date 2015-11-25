package tool.trans;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
		
		System.out.println(str1);
		
		String sign = MD5(str1);	
		
		System.out.println(sign);
		
		StringBuffer surl = new StringBuffer(URL);
		surl.append("q="+query);
		surl.append("&appid="+APPID);
		surl.append("&salt="+RANDOM);
		surl.append("&from=auto&to=auto&sign="+sign);
		
		System.out.println(surl);
		
		JSONObject jsonObject = WeixinUtil.doGetStr(surl.toString());
		
		String error_code = jsonObject.getString("error_code");
		if(error_code == null || !"52000".equals(error_code)){
			sb.append("翻译失败");
		}
		else{				//success
			back_info back_info = (back_info) JSONObject.toBean(jsonObject,back_info.class);
			sb.append("翻译结果为:");
			sb.append("原文："+back_info.getTrans_result().getSrc()+"\n");
			sb.append("译文："+back_info.getTrans_result().getDst()+"\n");
		}
		
		System.out.println(jsonObject);
		
		return sb.toString();
	}
	
	public static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
	
	
	

}

class back_info{
	private String from;
	private String to;
	private trans_result trans_result;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
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


