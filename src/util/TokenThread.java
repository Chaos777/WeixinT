package util;


import accesstoken.AccessToken;

public class TokenThread implements Runnable{
	
		
	
	public static AccessToken token = null;
	
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				token = WeixinUtil.getAccessToken();
				if(null != token){
					System.out.println("获取token成功");
					System.out.println(token.toString());
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
                    System.err.println(e1);
                }
				System.err.println( e);
			}
		}
	}

}
