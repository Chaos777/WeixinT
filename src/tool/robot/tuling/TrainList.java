package tool.robot.tuling;

public class TrainList {
	private String trainnum;
	private String start;
	private String terminal;
	private String starttime;
	private String endtime;
	private String icon;
	private String detailurl;
	public String getTrainnum() {
		return trainnum;
	}
	public void setTrainnum(String trainnum) {
		this.trainnum = trainnum;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
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
	public String getDetailurl() {
		return detailurl;
	}
	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}
	@Override
	public String toString() {
		return "TrainList [trainnum=" + trainnum + ", start=" + start
				+ ", terminal=" + terminal + ", starttime=" + starttime
				+ ", endtime=" + endtime + ", icon=" + icon + ", detailurl="
				+ detailurl + "]";
	}
	
	
}
