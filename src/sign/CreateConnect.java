package sign;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import servlet.InitServlet;
import tool.robot.tuling.TulingService;
import tool.today.TodayInHistoryService;
import tool.trans.Translate;
import tool.weather.WeatherForcast;
import tool.weather.WeatherResult;
import util.MessageUtil;

public class CreateConnect extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(signature == null||timestamp ==null ||nonce == null || echostr == null){
			out.print("消息传递失败");
		}
		else{		
			
			if(ToolUtil.checkSignature(signature, timestamp, nonce, echostr)){
				out.print(echostr);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();		
		try {
			
			Map<String, String> map = MessageUtil.xmlToMap(req);
			System.out.println(map);
			String ToUserName = map.get("ToUserName");
			String FromUserName = map.get("FromUserName");
			String CreateTime = map.get("CreateTime");
			String MsgType = map.get("MsgType");
			String Content = map.get("Content"); 
			
			
			String status = "0";
			
			if(InitServlet.mclient.keyExists(FromUserName)){
				status = (String) InitServlet.mclient.get(FromUserName);
				InitServlet.mclient.set(FromUserName, status, new Date(20*60*1000));
			}
			else{
				InitServlet.mclient.set(FromUserName, "0", new Date(20*60*1000));
			}
			
//			status = (String) InitServlet.mclient.get(FromUserName);
			String message = "";
			String con = "";
			if(MessageUtil.MESSAGE_VOICE.equals(MsgType)){
				con = map.get("Recognition");
				con = con.substring(0, con.indexOf("！"));
			}
			if(!"0".equals(status)&&MessageUtil.MESSAGE_TEXT.equals(MsgType)&&"退出模式".equals(Content)){
				InitServlet.mclient.set(FromUserName, "0", new Date(20*60*1000));
				message = MessageUtil.initText(ToUserName, FromUserName, "已成功退出特殊模式");
			}
			else if(!"0".equals(status)&&"退出模式".equals(con)){
				InitServlet.mclient.set(FromUserName, "0", new Date(20*60*1000));
				message = MessageUtil.initText(ToUserName, FromUserName, "已成功退出特殊模式");
			}			
			else{
				if("0".equals(status)){		//正常模式
					
//					message = MessageUtil.initText(ToUserName, FromUserName, "正常模式");
					if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
						if("1".equals(Content)){
							message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.firstMenu());

						}
						else if("2".equals(Content)){
//							message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.secondMenu());
							message = MessageUtil.initNewsMessage(ToUserName, FromUserName);	//图文消息

						}
						else if("3".equals(Content)){
							message = MessageUtil.initImageMessage(ToUserName, FromUserName);  	//图片消息

						}
						else if("4".equals(Content)){
							message = MessageUtil.initMusicMessage(ToUserName, FromUserName);  	//音乐消息
						}
						else if("5".equals(Content)){
							message = MessageUtil.initVoiceMessage(ToUserName, FromUserName);	//语音消息
						}
						else if("6".equals(Content)){											//翻译样例
							message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.threeMenu());
						}
						else if("7".equals(Content)){
							message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.fourMenu());
						}
						else if("8".equals(Content)){
							message = MessageUtil.initVideoMessage(ToUserName, FromUserName);	//视频消息
						}
						else if(Content.startsWith("翻译")){
							String word = Content.replaceAll("^翻译", "").trim();
							if("".equals(word)){
								message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.threeMenu());
							}
							else{
								message = MessageUtil.initText(ToUserName, FromUserName, Translate.translate(word));
							}
						}
						else if(Content.endsWith("天气")){
							String city = Content.replaceAll("天气$", "").trim();
							if("".equals(city)){
								message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.fourMenu());
							}
							else{
								String result = WeatherForcast.forcastByName(city);
								if("false".equals(result)){
									result = "sorry 您输入的城市暂时无法查询";
									message = MessageUtil.initText(ToUserName, FromUserName, result+"\n\n"+MessageUtil.fourMenu());
								}
								else{
									message = MessageUtil.initText(ToUserName, FromUserName, result);
								}
							}
						}
		/*				else if("today".equals(Content)||"Today".equals(Content)){				//历史上的今天
							String info = TodayInHistoryService.getTodayInHistoryInfo();
							message = MessageUtil.initText(ToUserName, FromUserName, info);
						}*/
						else if("?".equals(Content)||"？".equals(Content)){
							message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());	//文字消息

						}
					}
					else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
						String eventTyep = map.get("Event");
						if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventTyep)){		//关注事件
							message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
						}
						else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventTyep)){	//取消关注
							InitServlet.mclient.delete(FromUserName);
						}
						else if(MessageUtil.MESSAGE_CLICK.equals(eventTyep)){		//点击事件
							String key = map.get("EventKey");
							if("11".equals(key)){
								message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
							}
							else if ("33".equals(key)) {
								String info = TodayInHistoryService.getTodayInHistoryInfo();
								message = MessageUtil.initText(ToUserName, FromUserName, info);
							}
							else if ("34".equals(key)){
								InitServlet.mclient.set(FromUserName, "1", new Date(20*60*1000));
								message = MessageUtil.initText(ToUserName, FromUserName, "成功进入图灵机模式,输入\"退出模式\"退出");
							}
							else if ("35".equals(key)){
								InitServlet.mclient.set(FromUserName, "2", new Date(20*60*1000));
								message = MessageUtil.initText(ToUserName, FromUserName, "成功进入小黄鸡模式,输入\"退出模式\"退出");
							}
						}
						else if(MessageUtil.MESSAGE_VIEW.equals(eventTyep)){		//view事件
							String url = map.get("EventKey");
//							System.out.println(url);
							message = MessageUtil.initText(ToUserName, FromUserName, url);
						}
						else if(MessageUtil.MESSAGE_SCANCODE.equals(eventTyep)){	//扫码事件
							String key = map.get("EventKey");
							//System.out.println(key);
							message = MessageUtil.initText(ToUserName, FromUserName, key);
						}
					}
					else if(MessageUtil.MESSAGE_LOCATION.equals(MsgType)){				//地理位置
						String label = map.get("Label");
//						System.out.println(map);
						String result = WeatherForcast.forcastByLocation(map.get("Location_X"), map.get("Location_Y"));
						if(null == result || "".equals(result)){
							message = MessageUtil.initText(ToUserName, FromUserName, label);
						}
						else{
							message = MessageUtil.initText(ToUserName, FromUserName, result);
						}						

					}
					
					else if(MessageUtil.MESSAGE_VOICE.equals(MsgType)){		//语音消息
						String recognition = map.get("Recognition");
//						System.out.println(recognition);
						String contemp = recognition.substring(0, recognition.indexOf("！"));
//						System.out.println(contemp);
						if(contemp.endsWith("天气")){
							String city = contemp.replaceAll("天气$", "").trim();
							if("".equals(city)){
								String result = "您输入的语音识别结果为："+recognition;
								message = MessageUtil.initText(ToUserName, FromUserName, result);
							}
							else{
								String result = WeatherForcast.forcastByName(city);
								if("false".equals(result)){
									result = "您输入的语音识别结果为："+recognition;
									message = MessageUtil.initText(ToUserName, FromUserName, result);
								}
								else{
									message = MessageUtil.initText(ToUserName, FromUserName, result);
								}
							}
						}
						else if(contemp.startsWith("翻译")){
							String word = Content.replaceAll("^翻译", "").trim();
							if("".equals(word)){
								message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.threeMenu());
							}
							else{
								message = MessageUtil.initText(ToUserName, FromUserName, Translate.translate(word));
							}
						}
						else{
							String result = "您输入的语音识别结果为："+recognition;
							message = MessageUtil.initText(ToUserName, FromUserName, result);
						}
						
					}
				}
				
				else if("1".equals(status)){		//图灵机器人模式
					if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){		//输入文本
						String res = TulingService.TulingRun(Content);
						message = MessageUtil.initText(ToUserName, FromUserName, res);
					}
					else if(MessageUtil.MESSAGE_VOICE.equals(MsgType)){	//输入的是语音
						String recon = map.get("Recognition");
						String cont = recon.substring(0, recon.indexOf("！"));
						String res = TulingService.TulingRun(cont);
						message = MessageUtil.initText(ToUserName, FromUserName, res);						
					}
					else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
						String eventTyep = map.get("Event");
						if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventTyep)){	//取消关注
							InitServlet.mclient.delete(FromUserName);
						}
						else{
							String re = "您当前进入的是机器人聊天模式，只能输入文字，输入\"退出模式\"退出";
							message = MessageUtil.initText(ToUserName, FromUserName, re);
						}
					}
					else{
						String re = "您当前进入的是机器人聊天模式，只能输入文字，输入\"退出模式\"退出";
						message = MessageUtil.initText(ToUserName, FromUserName, re);
					}
					

				}
				else if("2".equals(status)) {		//小黄鸡模式
					message = MessageUtil.initText(ToUserName, FromUserName, "小黄鸡模式");
					if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
						
					}
					else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
						
					}
					else if(MessageUtil.MESSAGE_LOCATION.equals(MsgType)){
						
					}
				}
			}
			
			System.out.println(message);
			out.print(message);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			out.close();
		}
		
	}
	
	
	

}
