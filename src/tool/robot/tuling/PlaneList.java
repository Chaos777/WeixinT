package tool.robot.tuling;

public class PlaneList {
	private String flight;
	private String starttime;
	private String endtime;
	private String icon;
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String toString() {
		return "PlaneList [flight=" + flight + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", icon=" + icon + "]";
	}
	

}
