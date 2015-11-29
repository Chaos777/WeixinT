package tool.robot.tuling;

import java.util.Arrays;

public class NewsResult {
	private String code;
	private String text;
	private NewsList[] list;
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
	public NewsList[] getList() {
		return list;
	}
	public void setList(NewsList[] list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "NewsResult [code=" + code + ", text=" + text + ", list="
				+ Arrays.toString(list) + "]";
	}
	

}
