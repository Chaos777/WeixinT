package tool.robot.tuling;

import java.util.Arrays;

public class PlaneResult {
	private String code;
	private String text;
	private PlaneList[] list;
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
	public PlaneList[] getList() {
		return list;
	}
	public void setList(PlaneList[] list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PlaneResult [code=" + code + ", text=" + text + ", list="
				+ Arrays.toString(list) + "]";
	}
	

}
