package hanze.nl.bussimulator;

import hanze.nl.bussimulator.StopEnum.Position;

public class Bus {

	private BusMessageControler busMessageController;
	private Companies company;
	private Lines line;
	private int stopNumber;
	private int untilNextStop;
	private int direction;
	private boolean atStop;
	private String busID;

	Bus(Lines line, Companies company, int direction) {
		this.line = line;
		this.company = company;
		this.direction = direction;
		this.stopNumber = -1;
		this.untilNextStop = 0;
		this.atStop = false;
		this.busID = "Not started";
		busMessageController = new BusMessageControler(company, line, stopNumber, untilNextStop, direction, atStop,
				busID);
	}

	public BusMessageControler getMessageControler() {
		return busMessageController;
	}

	public void setbusID(int startTime) {
		this.busID = startTime + line.name() + direction;
	}

	public void toNextStop() {
		Position nextStop = line.getStop(stopNumber + direction).getPosition();
		untilNextStop = line.getStop(stopNumber).distance(nextStop);
	}

	public boolean stopReached() {
		stopNumber += direction;
		atStop = true;

		if ((stopNumber >= line.getLength() - 1) || (stopNumber == 0)) {
			System.out.printf("Bus %s has reached terminus (halte %s, richting %d).%n",
					line.name(), line.getStop(stopNumber), line.getDirection(stopNumber));
			return true;
		} else {
			System.out.printf("Bus %s has reached stop %s, direction %d.%n",
					line.name(), line.getStop(stopNumber), line.getDirection(stopNumber));
			toNextStop();
		}

		return false;
	}

	public void start() {
		stopNumber = (direction == 1) ? 0 : line.getLength() - 1;
		System.out.printf("Bus %s has left from stop %s in direction %d.%n",
				line.name(), line.getStop(stopNumber), line.getDirection(stopNumber));
		toNextStop();
	}

	public boolean move() {
		boolean terminusReached = false;
		atStop = false;

		if (stopNumber == -1) {
			start();
		} else {
			untilNextStop--;
			if (untilNextStop == 0) {
				terminusReached = stopReached();
			}
		}

		return terminusReached;
	}
}
