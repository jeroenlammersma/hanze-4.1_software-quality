package hanze.nl.bussimulator;

public class ETA {
	String stopName;
	int direction;
	int arrivalTime;

	ETA(String stopName, int direction, int arrivalTime) {
		this.stopName = stopName;
		this.direction = direction;
		this.arrivalTime = arrivalTime;
	}
}
