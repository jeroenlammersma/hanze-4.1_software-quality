package hanze.nl.tijdtools;

import java.io.IOException;
import com.thoughtworks.xstream.XStream;

public class TimeFunctions {
	private Time startTime;
	private Time simulationTime;
	private Time difference;
	private int interval;
	private int syncInterval;
	private int syncCounter;

	public void initSimulationTimes(int interval, int syncInterval) {
		simulationTime = new Time(0, 0, 0);
		startTime = getCentralTime();
		difference = calculateDifference(startTime, simulationTime);
		this.interval = interval;
		this.syncCounter = syncInterval;
		this.syncInterval = syncInterval;
	}

	public String getSimulationDisplayTime() {
		Time simulationDisplayTime = simulationTime.copyTime();
		simulationDisplayTime.increment(difference);
		return simulationDisplayTime.toString();
	}

	public int getCounter() {
		return calculateCounter(simulationTime);
	}

	public int getTimeCounter() {
		return calculateCounter(simulationTime) + calculateCounter(difference);
	}

	public void simulatorStep() throws InterruptedException {
		Thread.sleep(interval);
		simulationTime.increment(new Time(0, 0, 1));
		syncCounter--;
		if (syncCounter == 0) {
			syncCounter = syncInterval;
			synchronizeTime();
		}
	}

	private int calculateCounter(Time tijd) {
		return tijd.getHour() * 3600 + tijd.getMinute() * 60 + tijd.getSecond();
	}

	private Time calculateDifference(Time referenceTime, Time workTime) {
		int hoursDifference = referenceTime.getHour() - workTime.getHour();
		int minutesDifference = referenceTime.getMinute() - workTime.getMinute();
		int secondsDifference = referenceTime.getSecond() - workTime.getSecond();
		if (secondsDifference < 0) {
			minutesDifference--;
			secondsDifference += 60;
		}
		if (minutesDifference < 0) {
			hoursDifference--;
			minutesDifference += 60;
		}
		return new Time(hoursDifference, minutesDifference, secondsDifference);
	}

	private void synchronizeTime() {
		Time currentTime = getCentralTime();
		System.out.println("The current time is:  " + currentTime.toString());
		Time expectedSimulationTime = simulationTime.copyTime();
		expectedSimulationTime.increment(difference);
		Time delay = calculateDifference(currentTime, expectedSimulationTime);
		difference.increment(delay);
	}

	private Time getCentralTime() {
		try {
			HTTPFunctions httpFuncties = new HTTPFunctions();
			String result = httpFuncties.executeGet("xml");
			XStream xstream = new XStream();
			xstream.alias("Tijd", Time.class);
			Time time = (Time) xstream.fromXML(result);
			return time;

		} catch (IOException e) {
			e.printStackTrace();
			return new Time(0, 0, 0);
		}
	}
}
