package sign;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import tool.today.TodayInHistoryService;
import tool.trans.Translate;
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
			String ToUserName = map.get("ToUserName");
			String FromUserName = map.get("FromUserName");
			String CreateTime = map.get("CreateTime");
			String MsgType = map.get("MsgType");
			String Content = map.get("Content"); 
			
			String message = "";
			if(MessageUtil.MESSAGE_TEXT.equals(MsgType)){
				
				if("1".equals(Content)){
					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.firstMenu());

				}
				else if("2".equals(Content)){
//					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.secondMenu());
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
				else if(Content.startsWith("翻译")){
					String word = Content.replaceAll("^翻译", "").trim();
					if("".equals(word)){
						message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.threeMenu());
					}
					else{
						message = MessageUtil.initText(ToUserName, FromUserName, Translate.translate(word));
					}
				}
				else if("today".equals(Content)||"Today".equals(Content)){				//历史上的今天
					String info = TodayInHistoryService.getTodayInHistoryInfo();
					message = MessageUtil.initText(ToUserName, FromUserName, info);
				}
				else if("?".equals(Content)||"？".equals(Content)){
					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());	//文字消息

				}
				
				/*TextMessage text = new TextMessage();
				text.setFromUserName(ToUserName);
				text.setToUserName(FromUserName);
				text.setMsgType("text");
				text.setCreateTime(new Date().getTime()+"");
				text.setContent("接收到的消息是： "+Content);
				
				message = MessageUtil.textMessageToXml(text);
*/			}
			else if(MessageUtil.MESSAGE_EVENT.equals(MsgType)){
				String eventTyep = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventTyep)){		//关注事件
					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
				}
				else if(MessageUtil.MESSAGE_CLICK.equals(eventTyep)){		//点击事件
					message = MessageUtil.initText(ToUserName, FromUserName, MessageUtil.menuText());
				}
				else if(MessageUtil.MESSAGE_VIEW.equals(eventTyep)){		//view事件
					String url = map.get("EventKey");
					System.out.println(url);
					message = MessageUtil.initText(ToUserName, FromUserName, url);
				}
				else if(MessageUtil.MESSAGE_SCANCODE.equals(eventTyep)){	//扫码事件
					String key = map.get("EventKey");
					System.out.println(key);
					message = MessageUtil.initText(ToUserName, FromUserName, key);
				}
			}
			else if(MessageUtil.MESSAGE_LOCATION.equals(MsgType)){
				String label = map.get("Label");
				message = MessageUtil.initText(ToUserName, FromUserName, label);

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
