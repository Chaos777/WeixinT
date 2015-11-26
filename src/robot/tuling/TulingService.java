package robot.tuling;

import util.WeixinUtil;
import net.sf.json.JSONObject;

public class TulingService {
	
	private static final String URL = "http://www.tuling123.com/openapi/api";
	private static final String API = "2f74c2b657ee226cd73bde5b3537bf10";
	
	public static String TulingRun(String content){
		StringBuffer sb = new StringBuffer();
		String url = URL+"?key="+API+"&info="+content;
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		System.out.println(jsonObject);
		
		return sb.toString();
	}
	
	
	public static void main(String args[]){
		TulingRun("你好");
	}
	

}
