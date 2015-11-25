package tool.weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import sign.Encode;
import util.WeixinUtil;

public class WeatherForcast {
	
	private static String BASE_URL = "http://api.map.baidu.com/telematics/v3/weather";
	private static String AK = "XZWqDxzFwawX5Dxt8GrZFFrc";
	private static String SK = "LOADFomNe0PIhcBe7gkhB6PtxvkQCje6";
	
	
	public static String forcastByLocation(String lx, String ly){
		StringBuffer sb = new StringBuffer();
		String url = BASE_URL+"?location="+ly+","+lx+"&output=json&ak="+AK;
		
//		System.out.println(url);
		JSONObject jsonObject = WeixinUtil.doGetStr(url);
		
		String error_code = jsonObject.getString("error");
		
		if("0".equals(error_code)&&"success".equalsIgnoreCase(jsonObject.getString("status"))){
			
/*			for(Object object : jsonObject.entrySet()){
				Map.Entry entry = (Map.Entry) object;  
				String propertyName = entry.getKey().toString();  
				String propertyValue = entry.getValue().toString();
				
				System.out.println(propertyName+"   "+propertyValue);
			}*/
			JSONArray jsonArray = jsonObject.getJSONArray("results");
			JSONObject jObject = jsonArray.getJSONObject(0);
			System.out.println(jObject);
			
		/*	Results results = jObject.get("");
			System.out.println(results);
			Weather_data[] eDatas = results.getWeather_data();
			for(Weather_data wd:eDatas){
				System.out.println(wd);
			}*/
			
/*			sb.append("当前城市是："+weatherResult.getResults().getCurrentCity()+"\n");
			sb.append("pm25为："+weatherResult.getResults().getPm25()+"\n");
			sb.append("天气详情为：\n");
			
			for(Weather_data wd:weatherResult.getResults().getWeather_data()){
				sb.append(wd.getDate()+" "+wd.getWeather()+" "+wd.getTemperature()+"\n");
			}
			sb.append("\n\n");*/
		}
		else{
			
		}
		
//		System.out.println(jsonObject);
		
		return sb.toString();
	}
	
	public static String forcastByName(String city) throws UnsupportedEncodingException{
		StringBuffer sb = new StringBuffer();
		String url = BASE_URL+"?location="+URLEncoder.encode(city,"utf-8")+"&output=json&ak="+AK;
		JSONObject jsonObject = WeixinUtil.doGetStr(url);	
		
		String error_code = jsonObject.getString("error");
		
		if("0".equals(error_code)&&"success".equalsIgnoreCase(jsonObject.getString("status"))){
			WeatherResult weatherResult = (WeatherResult) JSONObject.toBean(jsonObject,WeatherResult.class);
			
			sb.append("当前城市是："+weatherResult.getResults().getCurrentCity()+"\n");
			sb.append("pm25为："+weatherResult.getResults().getPm25()+"\n");
			sb.append("天气详情为：\n");
			
			for(Weather_data wd:weatherResult.getResults().getWeather_data()){
				sb.append(wd.getDate()+" "+wd.getWeather()+" "+wd.getTemperature()+"\n");
			}
			sb.append("\n\n");
		}
		else{
			sb.append("false");
		}
		return sb.toString();
	}
	
	public static String getSN(){
		StringBuffer sn = new StringBuffer("");
		
		try {
			sn.append(Encode.MD5(URLEncoder.encode(BASE_URL+SK,"utf-8")).toLowerCase());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sn.toString();
	}
	
	
	
	
	
	
	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
//		System.out.println(forcastByLocation("30.265915", "119.950241"));
		System.out.println(forcastByName("缙云"));
	}

}
