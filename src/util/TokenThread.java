package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accesstoken.AccessToken;

public class TokenThread implements Runnable{
	
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);	
	
	public static AccessToken token = null;
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				token = WeixinUtil.getAccessToken();
				if(null != token){
					log.info("获取token成功");
					log.info(token.toString());
					Thread.sleep((Integer.valueOf(token.getExpires_in())-200)*1000);
				}
				else{
					Thread.sleep(60*1000);
				}
			}
			catch (InterruptedException e) {
				// TODO: handle exception
				try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
			}
		}
	}

}
