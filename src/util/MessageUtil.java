package util;

import imagemessage.Image;
import imagemessage.ImageMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import musicmessage.Music;
import musicmessage.MusicMessage;
import newsmessage.News;
import newsmessage.NewsMessage;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import textmessage.TextMessage;
import videomessage.Video;
import videomessage.VideoMessage;
import voicemessage.Voice;
import voicemessage.VoiceMessage;

import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	public static final String MESSAGE_LOCATION_SELECT = "location_select";
	
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		
		Element root = doc.getRootElement();
		
		List<Element> list = root.elements();
		
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		
		ins.close();
		
		return map;
	}
	
	//文本消息转xml
	public static String textMessageToXml(TextMessage textMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		
		return xstream.toXML(textMessage);		
	
	}
	
	//文本消息组装
	public static String initText(String ToUserName,String FromUserName,String Content){
		TextMessage text = new TextMessage();
		text.setFromUserName(ToUserName);
		text.setToUserName(FromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime()+"");
		text.setContent(Content);
		
		return textMessageToXml(text);
	}
	
	//主菜单
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎关注，请按照菜单提示操作:\n\n");
		sb.append("1,介绍\n");
		sb.append("2,自己介绍(图文)\n");
		sb.append("3,自己介绍(图片)\n");
		sb.append("4,音乐\n");
		sb.append("5,语音\n");
		sb.append("6,翻译\n");
		sb.append("7,天气\n");
		sb.append("8,视频\n\n");
		sb.append("回复？调出菜单。");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("介绍内容就是这个");		
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("自己介绍的内容就是这个");		
		return sb.toString();
	}
	
	public static String threeMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("词组翻译使用指南\n\n");		
		sb.append("使用实例\n");		
		sb.append("翻译足球\n");		
		sb.append("翻译中国足球\n");		
		sb.append("翻译football\n");	
		/*sb.append("翻译さようなら\n\n");*/
		sb.append("回复？调出菜单。");
		return sb.toString();
	}
	
	public static String fourMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("天气查询使用指南\n\n");
		sb.append("使用实例\n");
		sb.append("杭州天气\n");
		sb.append("北京天气\n");
		return sb.toString();
	}
	
	//图文消息转xml
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		
		return xstream.toXML(newsMessage);		
	
	}
	
	
	//图文消息组装
	public static String initNewsMessage(String ToUserName,String FromUserName){
		String message = "";
		List<News> newsList = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		
		for(int i=1;i<9;i++){
		
			News news = new News();
			news.setTitle("标题"+i);
			news.setDescription("描述内容"+i);
			news.setPicUrl("http://ww4.sinaimg.cn/bmiddle/6cd6d028jw1eyuv3tcqpyj20go08lwev.jpg");
			news.setUrl("qq.com");	//跳转的url
			
			newsList.add(news);
		}
		
		newsMessage.setToUserName(FromUserName);
		newsMessage.setFromUserName(ToUserName);
		newsMessage.setCreateTime(new Date()+"");
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		
		message = newsMessageToXml(newsMessage);
		
		return message;
	}
	
	//图片消息转xml
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
					
		return xstream.toXML(imageMessage);		
		
	}
	
	//图片消息组装
	public static String initImageMessage(String ToUserName,String FromUserName){
		String message = "";
		Image image = new Image();
//		image.setMediaId("UxPzF70QF_2_mqqWcg9koBakKSi2w49nre3_Af9_kZCyBgZQALY5zEYDuEdP2nhx");
		image.setMediaId("aE5zWuoxxyu_3kAJPh72tFGRxB_BUta7Ocy5Snhqc2Q");	//永久图片信息ID
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(ToUserName);
		imageMessage.setToUserName(FromUserName);
		imageMessage.setImage(image);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime()+"");
		
		message = imageMessageToXml(imageMessage);
		return message;
	}
	
	//音乐消息转xml
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", musicMessage.getClass());
						
		return xstream.toXML(musicMessage);		
			
	}
	
	//音乐消息组装
	public static String initMusicMessage(String ToUserName,String FromUserName){
		String message = "";
		Music music = new Music();
//		music.setThumbMediaId("97yJiFpLckaA2v_Gq_gQDQn7UQxPBskAyk8BQ7AgL0qYNiO1UiKbYKvJnR2iTivw");	//临时
		music.setThumbMediaId("aE5zWuoxxyu_3kAJPh72tKKlIF92pUVgKtA0SwSuF-M");			//永久
		music.setTitle("see you again");
		music.setDescription("Fast & Furious");
		music.setMusicUrl("http://chaos.tunnel.qydev.com/WeixinT/resource/Seeyouagain.mp3");
		music.setHQMusicUrl("http://chaos.tunnel.qydev.com/WeixinT/resource/Seeyouagain.mp3");
		
		MusicMessage musicMessage = new MusicMessage();
		musicMessage.setFromUserName(ToUserName);
		musicMessage.setToUserName(FromUserName);
		musicMessage.setMusic(music);
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setCreateTime(new Date().getTime()+"");
			
		message = musicMessageToXml(musicMessage);
		return message;
	}
	
	//语音消息转xml
	public static String voiceMessageToXml(VoiceMessage voiceMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", voiceMessage.getClass());							
		return xstream.toXML(voiceMessage);		
				
	}
	
	//语音消息组装
	public static String initVoiceMessage(String ToUserName,String FromUserName){
		String message = "";
		Voice voice = new Voice();
//		voice.setMediaId("74Yl0XM275IGHkJDWlQA6Ao1aLS_pIvDRSLQVsD6kRCtVCzpkwzqAqVIv2Imxi-g");		//临时
		voice.setMediaId("httopkKF6YnVF-D4axZ8A-2AdlG2hjvi_Mwr1v_JUs0");
		VoiceMessage voiceMessage = new VoiceMessage();
		voiceMessage.setFromUserName(ToUserName);
		voiceMessage.setToUserName(FromUserName);
		voiceMessage.setVoice(voice);
		voiceMessage.setMsgType(MESSAGE_VOICE);
		voiceMessage.setCreateTime(new Date().getTime()+"");
			
		message = voiceMessageToXml(voiceMessage);
		return message;
	}
	
	//视频消息转xml
	public static String videoMessageToXml(VideoMessage videoMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", videoMessage.getClass());							
		return xstream.toXML(videoMessage);						
	}
	
	//视频消息组装
	public static String initVideoMessage(String ToUserName,String FromUserName){
		String message = "";
		Video video = new Video();
/*		video.setMediaId("33Z-2XfcQGh2ce49RUbUw0jVBNXteEbfCIECyIqFsOyhSHboUVftWlmU0YDJjsTx");	//临时
		video.setTitle("穆里尼奥");
		video.setDescription("上帝第一我第二！ 细数穆里尼奥十大经典时刻");*/
		video.setMediaId("H7opIbEyVCZi8fuHW6O4qJJBs1ug8Divaf_ZsTI3gItyZlQjs9K0IcrBq72CzFBA");	//lingshi
		video.setTitle("NBA");
		video.setDescription("NBA TOP5");
		VideoMessage videoMessage = new VideoMessage();
		videoMessage.setFromUserName(ToUserName);
		videoMessage.setToUserName(FromUserName);
		videoMessage.setVideo(video);
		videoMessage.setMsgType(MESSAGE_VIDEO);
		videoMessage.setCreateTime(new Date().getTime()+"");
				
		message = videoMessageToXml(videoMessage);
		return message;
	}
	
	

}
