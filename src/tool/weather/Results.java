package tool.weather;

import java.util.Arrays;

public class Results {
	private String currentCity;
	private String pm25;
	private Index[] index;
	private Weather_data[] weather_data;
	
	public String getCurrentCity() {
		return currentCity;
	}
	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public Index[] getIndex() {
		return index;
	}
	public void setIndex(Index[] index) {
		this.index = index;
	}
	public Weather_data[] getWeather_data() {
		return weather_data;
	}
	public void setWeather_data(Weather_data[] weather_data) {
		this.weather_data = weather_data;
	}
	@Override
	public String toString() {
		return "Results [currentCity=" + currentCity + ", pm25=" + pm25
				+ ", index=" + Arrays.toString(index) + ", weather_data="
				+ Arrays.toString(weather_data) + "]";
	}

}
