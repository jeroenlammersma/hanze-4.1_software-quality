package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;
import hanze.nl.bussimulator.StopEnum.Position;

public class Bus {

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

	public void sendETAs(int now) {
		int position = 0;
		BusMessage message = new BusMessage(line.name(), company.name(), busID, now);
		if (atStop)
			addETAToMessage(line, position, message, 0);

		Position nextStop = line.getStop(stopNumber + direction).getPosition();
		int timeToStop = untilNextStop + now;

		for (position = stopNumber + direction; !(position >= line.getLength())
				&& !(position < 0); position = position + direction) {
			timeToStop += line.getStop(position).distance(nextStop);
			addETAToMessage(line, position, message, timeToStop);
			nextStop = line.getStop(position).getPosition();
		}

		message.terminus = line.getStop(position - direction).name();
		sendMessage(message);
	}

	private void addETAToMessage(Lines line, int position, BusMessage message, int arrivalTime) {
		ETA eta = new ETA(line.getStop(position).name(), line.getDirection(position), arrivalTime);
		message.ETAs.add(eta);
	}

	public void sendLastETA(int now) {
		BusMessage message = new BusMessage(line.name(), company.name(), busID, now);
		String terminus = line.getStop(stopNumber).name();
		ETA eta = new ETA(terminus, line.getDirection(stopNumber), 0);
		message.ETAs.add(eta);
		message.terminus = terminus;
		sendMessage(message);
	}

	public void sendMessage(BusMessage message) {
		XStream xstream = new XStream();
		xstream.alias("Message", BusMessage.class);
		xstream.alias("ETA", ETA.class);
		String xml = xstream.toXML(message);
		Producer producer = new Producer();
		producer.sendMessage(xml);
	}
}
