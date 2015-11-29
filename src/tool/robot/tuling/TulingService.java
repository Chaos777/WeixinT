package tool.robot.tuling;

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
		
		String code = jsonObject.getString("code");
		if(code.startsWith("4")){
			sb.append("非常抱歉，服务器正在升级，请稍后再试！");
		}
		else if(code.startsWith("1")){
			if("100000".equals(code)){
				TextResult textResult = (TextResult)JSONObject.toBean(jsonObject, TextResult.class);
//				System.out.println(textResult);		
				sb.append(textResult.getText()+"\n");
			}
		}
		else if(code.startsWith("2")){
			if("200000".equals(code)){
				LinkResult linkResult = (LinkResult) JSONObject.toBean(jsonObject, LinkResult.class);
//				System.out.println(linkResult);
				sb.append(linkResult.getText()+"\n");
				sb.append(linkResult.getUrl()+"\n");
			}
		}
		else if(code.startsWith("3")){
			if("302000".equals(code)){
				NewsResult newsResult = (NewsResult)JSONObject.toBean(jsonObject, NewsResult.class);
//				System.out.println(newsResult);
				sb.append(newsResult.getText()+"\n\n");
				NewsList[] list = newsResult.getList();
				for(NewsList n:list){
//					sb.append(+"\n");
//					sb.append(+"\n");
					sb.append("<a href=\""+n.getDetailurl()+"\">"+n.getArticle()+" "+n.getSource()+"-->详情</a>"+"\n\n");
				}
			}
			else if("305000".equals(code)){
				TrainResult trainResult = (TrainResult) JSONObject.toBean(jsonObject, TrainResult.class);
				System.out.println(trainResult);
			}
			else if("306000".equals(code)){
				PlaneResult planeResult = (PlaneResult)JSONObject.toBean(jsonObject, PlaneResult.class);
				System.out.println(planeResult);
			}
			else if("308000".equals(code)){
				CookResult cookResult = (CookResult) JSONObject.toBean(jsonObject, CookResult.class);
//				System.out.println(cookResult);
				sb.append(cookResult.getText());
				CookList[] lists = cookResult.getList();
				int count = 0;
				for(CookList cook:lists){
					if(++count>5){
						break;
					}
					sb.append("\n"+cook.getName()+"\n");
//					sb.append(cook.getIcon()+"\n");
					sb.append(cook.getInfo().replaceAll(" \\| ", "/")+"\n");
					sb.append("<a href=\""+cook.getDetailurl()+"\">"+"详见--></a>"+"\n");
				}
			}
			
			
		}
		
		return sb.toString();
	}
	
	
	public static void main(String args[]){
		System.out.println(TulingRun("鱼香肉丝"));
	}
	

}
