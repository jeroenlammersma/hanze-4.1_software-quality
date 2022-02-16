package hanze.nl.infobord;

import hanze.nl.tijdtools.InfoboardTimeFunctions;

public class JSONMessage {
	public int time;
	public int arrivalTime;
	public String lineName;
	public String busID;
	public String company;
	public String terminus;

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
