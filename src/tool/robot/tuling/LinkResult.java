package tool.robot.tuling;

public class LinkResult {
	private String code;
	private String text;
	private String url;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "LinkResult [code=" + code + ", text=" + text + ", url=" + url
				+ "]";
	}
	

}
