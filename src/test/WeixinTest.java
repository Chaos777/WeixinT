package test;

import net.sf.json.JSONObject;
import util.TokenThread;
import util.WeixinUtil;
import accesstoken.AccessToken;

public class WeixinTest {
	
	public static void main(String args[]){
		AccessToken token = WeixinUtil.getAccessToken();
		
//		AccessToken token = TokenThread.token;		//通过线程定时执行获取token
		System.out.println("Token: "+token.getToken());
		System.out.println("有效时间: "+token.getExpires_in());
		
		String path = "D:/10.mp3";		//音乐语音图片视频消息是临时上传的，有效期3天，需要重新上传
		try {
			String mediaId = WeixinUtil.postFile(path, token.getToken(), "voice","title","introduction");
			System.out.println(mediaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
		int result = WeixinUtil.createMenu(token.getToken(), menu);
		if(result == 0){
			System.out.println("创建菜单成功");
			
		}
		else{
			System.out.println(result);
		}*/
		
		/*JSONObject jsonObject = WeixinUtil.queryMenu(token.getToken());
		System.out.println(jsonObject);*/
	}
	
	

}
