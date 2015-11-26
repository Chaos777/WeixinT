package tool.weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
			WeatherResult weatherResult = (WeatherResult) JSONObject.toBean(jsonObject,WeatherResult.class);
			Results[] results = weatherResult.getResults();
//			System.out.println(results.length);
			sb.append("当前城市是："+results[0].getCurrentCity()+"\n");
			sb.append("pm25为："+results[0].getPm25()+"\n");
			sb.append("天气详情\n");
			
			for(Weather_data wd:results[0].getWeather_data()){
				sb.append(wd.getDate()+" "+wd.getWeather()+" "+wd.getTemperature()+"\n");
			}
			sb.append("\n\n");
//			System.out.println(sb);
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
			Results[] results = weatherResult.getResults();
//			System.out.println(results.length);
			sb.append("当前城市是："+results[0].getCurrentCity()+"\n");
			sb.append("pm25为："+results[0].getPm25()+"\n");
			sb.append("天气详情\n");
			
			for(Weather_data wd:results[0].getWeather_data()){
				sb.append(wd.getDate()+" "+wd.getWeather()+" "+wd.getTemperature()+"\n");
			}
			sb.append("\n\n");
//			System.out.println(weatherResult);
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
		System.out.println(forcastByLocation("30.265915", "119.950241"));
//		System.out.println(forcastByName("杭州"));
	}

}
