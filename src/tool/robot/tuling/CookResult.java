package tool.robot.tuling;

import java.util.Arrays;

public class CookResult {
	private String code;
	private String text;
	private CookList[] list;
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
	public CookList[] getList() {
		return list;
	}
	public void setList(CookList[] list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "CookResult [code=" + code + ", text=" + text + ", list="
				+ Arrays.toString(list) + "]";
	}
	

}
