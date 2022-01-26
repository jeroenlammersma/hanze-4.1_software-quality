package hanze.nl.bussimulator;

import java.util.ArrayList;

public class BusMessage {
	public String lineName;
	public String terminus;
	public String company;
	public String busID;
	public int time;
	public ArrayList<ETA> ETAs;

	BusMessage(String lineName, String company, String busID, int time) {
		this.lineName = lineName;
		this.company = company;
		this.terminus = "";
		this.busID = busID;
		this.time = time;
		this.ETAs = new ArrayList<ETA>();
	}
}
