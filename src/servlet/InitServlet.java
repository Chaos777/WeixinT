package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import util.TokenThread;

public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1417022156477133421L;
	public static MemCachedClient mclient = new MemCachedClient();
	
	 public void init() throws ServletException {
	      
		  new Thread(new TokenThread()).start();			//获取token
		  initMemCached();      							//设置用户模式,启动memCached
	        
	 }
	 
	 public static void initMemCached(){
		    String[] servers = { "localhost:11211" };
			SockIOPool pool = SockIOPool.getInstance();
			pool.setServers(servers);
			pool.setFailback(true);
			pool.setInitConn(10);
			pool.setMinConn(5);
			pool.setMaxConn(200);
			pool.setMaintSleep(30);
			pool.setNagle(false);
			pool.setSocketTO(3000);
			pool.setAliveCheck(true);
			pool.initialize();
			
/*			for (int i = 0; i < 10; i++) {
				boolean success = mclient.set("key" + i, "value" + i+1);
				System.out.println(String.format("set key( %d ): %s", i, success));
			}*/
	 }
	 
	 public static void main(String args[]){
		 String[] servers = { "localhost:11211" };
			SockIOPool pool = SockIOPool.getInstance();
			pool.setServers(servers);
			pool.setFailback(true);
			pool.setInitConn(10);
			pool.setMinConn(5);
			pool.setMaxConn(200);
			pool.setMaintSleep(30);
			pool.setNagle(false);
			pool.setSocketTO(3000);
			pool.setAliveCheck(true);
			pool.initialize();		
			

			MemCachedClient mClient = new MemCachedClient();
			for (int i = 0; i < 10; i++) {
				boolean success = mClient.set("key" + i, "value" + i+1);
				System.out.println(String.format("set key( %d ): %s", i, success));
			}

			for (int i = 9; i >= 0; i--) {
				String result = (String) mClient.get("key" + i);
				System.out.println(String.format("get key( %d ): %s", i, result));

			}
	 }

}
