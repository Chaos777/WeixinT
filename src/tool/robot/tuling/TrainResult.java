package tool.robot.tuling;

import java.util.Arrays;

public class TrainResult {
	
	private String code;
	private String text;
	private TrainList[] list;
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
	public TrainList[] getList() {
		return list;
	}
	public void setList(TrainList[] list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "TrainResult [code=" + code + ", text=" + text + ", list="
				+ Arrays.toString(list) + "]";
	}
	

}
