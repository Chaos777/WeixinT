package util;

 import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import accesstoken.AccessToken;

import menu.Button;
import menu.ClickButton;
import menu.Menu;
import menu.ViewButton;
import net.sf.json.JSONObject;

public class WeixinUtil {
	
//	private static final String APPID = "wx3714ce85dfc07c36";	//真实
	private static final String APPID = "wx61a87fb73a296db6";	//测试
//	private static final String APPSECRET = "1f8a48a83199931590d74262af136c75";		//真实
	private static final String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";		//测试
	
	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";	//临时
	private static final String UPLOAD_URL_EVER = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN&type=TYPE";	//永久，除图文素材外
	
	private static final String ACCESS_TOHEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	//get请求
	public static JSONObject doGetStr(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"utf-8");
				jsonObject = JSONObject.fromObject(result);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
	}
	
	//get string
	public static String doGetStr_(String url){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		StringBuffer sb = new StringBuffer();
		HttpGet httpGet = new HttpGet(url);
		
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if(entity != null){
				sb.append(EntityUtils.toString(entity,"utf-8"));				
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
	//post请求
	public static JSONObject doPostStr(String url,String outStr){
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try {
			httpPost.setEntity(new StringEntity(outStr, "utf-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"utf-8");
			jsonObject = JSONObject.fromObject(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 文件上传临时
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		if("voice".equals(type)){
			typeName = "media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	
	/**
	 * 素材上传永久 除图文素材外
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String uploadEver(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL_EVER.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		
		URL urlObj = new URL(url);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST"); 
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); 

		//设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		//设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filelength=\""+
					file.length()+"\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		//获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//输出表头
		out.write(head);

		//文件正文部分
		//把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		//结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			//定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		if("voice".equals(type)){
			typeName = "media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	public static String postFile(String filePath,String accessToken,String type ,String title,String introduction) {
			File file = new File(filePath);
			if(!file.exists())
			return null;
			String result = null;
			try {
				String url = UPLOAD_URL_EVER.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
				URL url1 = new URL(url); 
				HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(30000);  
				            conn.setDoOutput(true);  
				            conn.setDoInput(true);  
				            conn.setUseCaches(false);  
				            conn.setRequestMethod("POST"); 
				            conn.setRequestProperty("Connection", "Keep-Alive");
				            conn.setRequestProperty("Cache-Control", "no-cache");
				            String boundary = "-----------------------------"+System.currentTimeMillis();
				            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+boundary);
				             
				OutputStream output = conn.getOutputStream();
				output.write(("--" + boundary + "\r\n").getBytes());
				output.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName()).getBytes());  
				output.write("Content-Type: video/mp4 \r\n\r\n".getBytes());
				        byte[] data = new byte[1024];
				        int len =0;
				        FileInputStream input = new FileInputStream(file);
				while((len=input.read(data))>-1){
				output.write(data, 0, len);
				}
				output.write(("--" + boundary + "\r\n").getBytes());
				output.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
//				output.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}",title,introduction).getBytes());
				output.write(("\r\n--" + boundary + "--\r\n\r\n").getBytes());
				output.flush();
				output.close();
				input.close();			
				InputStream resp = conn.getInputStream();
				StringBuffer sb = new StringBuffer();
				while((len= resp.read(data))>-1)
				sb.append(new String(data,0,len,"utf-8"));
				resp.close();
				result = sb.toString();
				System.out.println(result);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		return result;
	}
	
	//获取acces_token
	public static AccessToken getAccessToken(){
		AccessToken token = new AccessToken();
		String url = ACCESS_TOHEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject!=null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getString("expires_in"));
		}
		return token;
	}
	
	//菜单组装
	public static Menu initMenu(){
		Menu menu = new Menu();
		ClickButton button11 = new ClickButton();
		button11.setName("click菜单");
		button11.setType("click");
		button11.setKey("11");
		
		
		
		ViewButton button21 = new ViewButton();
		button21.setName("神经猫");
		button21.setType("view");
		button21.setUrl("http://chaos.tunnel.qydev.com/WeixinT/sjm.html");
		
		ViewButton button22 = new ViewButton();
		button22.setName("2048");
		button22.setType("view");
		button22.setUrl("http://chaos.tunnel.qydev.com/WeixinT/2048.html");
		
		ViewButton button23 = new ViewButton();
		button23.setName("德州扑克");
		button23.setType("view");
		button23.setUrl("http://chaos.tunnel.qydev.com/WeixinT/tex.htm");
		
		ViewButton button24 = new ViewButton();
		button24.setName("水果忍者");
		button24.setType("view");
		button24.setUrl("http://chaos.tunnel.qydev.com/WeixinT/fruitNJ.html");
		
		ViewButton button25 = new ViewButton();
		button25.setName("打飞机");
		button25.setType("view");
		button25.setUrl("http://chaos.tunnel.qydev.com/WeixinT/plane.html");
		
		Button button2 = new Button();
		button2.setName("游戏");		
		button2.setSub_button(new Button[]{button21,button22,/*button23,button24,*/button25});
		
		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");		
		button31.setType("scancode_push");
		button31.setKey("31");
		
		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");		
		button32.setType("location_select");
		button32.setKey("32");
		
		ClickButton button33 = new ClickButton();
		button33.setName("历史上的今天");
		button33.setType("click");
		button33.setKey("33");
		
		ClickButton button34 = new ClickButton();
		button34.setName("图灵机器人");
		button34.setType("click");
		button34.setKey("34");
		
		ClickButton button35 = new ClickButton();
		button35.setName("小黄鸡");
		button35.setType("click");
		button35.setKey("35");		
		
		
		
		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[]{button31,button32,button33,button34/*,button35*/});
		
		menu.setButton(new Button[]{button11,button2,button});
		
		
		return menu;
	}
	
	public static int createMenu(String token,String menu){
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		int result = 0;
		
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		
		return result;
	}
	
	//查询菜单
	public static JSONObject queryMenu(String token){
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	//删除菜单
	public static JSONObject deleteMenu(String token){
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}

}
