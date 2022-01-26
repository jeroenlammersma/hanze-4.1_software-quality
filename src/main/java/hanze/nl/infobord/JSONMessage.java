package hanze.nl.infobord;

import hanze.nl.tijdtools.InfoboardTimeFunctions;

public class JSONMessage {
	private int time;
	private int arrivalTime;
	private String lineName;
	private String busID;
	private String company;
	private String terminus;

	public JSONMessage(int time, int arrivalTime, String lineName, String busID, String company, String teminus) {
		super();
		this.time = time;
		this.arrivalTime = arrivalTime;
		this.lineName = lineName;
		this.busID = busID;
		this.company = company;
		this.terminus = teminus;
	}

	public JSONMessage() {

	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String setTerminus() {
		return terminus;
	}

	public void setTerminus(String endpoint) {
		this.terminus = endpoint;
	}

	public String GetInfoLine() {
		InfoboardTimeFunctions timeFunctions = new InfoboardTimeFunctions();
		String time = timeFunctions.getFormattedTimeFromCounter(arrivalTime);
		String line = String.format("%8s - %5s - %12s", this.lineName, this.terminus, time);
		return line;
	}

	@Override
	public String toString() {
		return "JSONMessage [time=" + time + ", arrivaltime=" + arrivalTime + ", linename=" + lineName + ", busID="
				+ busID + ", company=" + company + ", terminus=" + terminus + "]";
	}
}
