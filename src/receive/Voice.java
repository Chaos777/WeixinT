package receive;

import basemessage.BaseMessage;

public class Voice extends BaseMessage{
	private String MediaId;
	private String Format;
	private String MsgId;
	private String Recognition;
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	@Override
	public String toString() {
		return "Voice [MediaId=" + MediaId + ", Format=" + Format + ", MsgId="
				+ MsgId + ", Recognition=" + Recognition + "]";
	}
	
}
