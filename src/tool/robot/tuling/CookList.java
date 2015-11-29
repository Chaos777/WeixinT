package tool.robot.tuling;

public class CookList {
	private String name;
	private String icon;
	private String info;
	private String detailurl;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDetailurl() {
		return detailurl;
	}
	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}
	@Override
	public String toString() {
		return "CookList [name=" + name + ", icon=" + icon + ", info=" + info
				+ ", detailurl=" + detailurl + "]";
	}
	
	

}
