package robot.xiaodoubi;

import util.WeixinUtil;

public class SIMIService {
	
	private static final String URL = "http://api.xiaodoubi.com/api.php?";
	private static final String API = "15714DD5-478B-A050-52F9-0D2BE87BA0DF";
	
	
	public static String SimiRun(String content){
		StringBuffer sb = new StringBuffer();
		String url = URL+"key="+API+"&chat="+content;
		String result = WeixinUtil.doGetStr_(url);
		System.out.println(result);
		return sb.toString();
	}	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimiRun("你好是谁啊啊啊啊");
	}

}
