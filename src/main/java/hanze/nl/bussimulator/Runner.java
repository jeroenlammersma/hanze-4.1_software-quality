package hanze.nl.bussimulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import hanze.nl.tijdtools.TimeFunctions;

public class Runner {

	private static HashMap<Integer, ArrayList<Bus>> busStart = new HashMap<Integer, ArrayList<Bus>>();
	private static ArrayList<Bus> activeBusses = new ArrayList<Bus>();
	private static int interval = 1000;
	private static int syncInterval = 5;

	private static void addBus(int startTime, Bus bus) {
		ArrayList<Bus> busses = new ArrayList<Bus>();
		if (busStart.containsKey(startTime)) {
			busses = busStart.get(startTime);
		}
		busses.add(bus);
		busStart.put(startTime, busses);
		bus.setbusID(startTime);
	}

	private static int startBusses(int time) {
		for (Bus bus : busStart.get(time)) {
			activeBusses.add(bus);
		}
		busStart.remove(time);
		return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
	}

	public static void moveBusses(int now) {
		Iterator<Bus> itr = activeBusses.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			boolean terminusReached = bus.move();
			if (terminusReached) {
				bus.getMessageControler().sendLastETA(now);
				itr.remove();
			}
		}
	}

	public static void sendETAs(int now) {
		Iterator<Bus> itr = activeBusses.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			bus.getMessageControler().sendETAs(now);
		}
	}

	public static int initBusses() {
		initBusLine(Lines.LIJN1, Companies.ARRIVA, 3);
		initBusLine(Lines.LIJN2, Companies.ARRIVA, 5);
		initBusLine(Lines.LIJN3, Companies.ARRIVA, 4);
		initBusLine(Lines.LIJN4, Companies.FLIXBUS, 6);
		initBusLine(Lines.LIJN5, Companies.QBUZZ, 3);
		initBusLine(Lines.LIJN6, Companies.QBUZZ, 5);
		initBusLine(Lines.LIJN7, Companies.ARRIVA, 4);
		initBusLine(Lines.LIJN1, Companies.ARRIVA, 6);
		initBusLine(Lines.LIJN4, Companies.ARRIVA, 12);
		initBusLine(Lines.LIJN5, Companies.FLIXBUS, 10);
		return Collections.min(busStart.keySet());
	}

	private static void initBusLine(Lines line, Companies company, int time) {
		Bus bus1 = new Bus(line, company, 1);
		Bus bus2 = new Bus(line, company, -1);
		addBus(time, bus1);
		addBus(time, bus2);
	}

	public static void main(String[] args) throws InterruptedException {
		int time = 0;
		int counter = 0;
		TimeFunctions timeFunctions = new TimeFunctions();
		timeFunctions.initSimulationTimes(interval, syncInterval);
		int next = initBusses();
		while ((next >= 0) || !activeBusses.isEmpty()) {
			counter = timeFunctions.getCounter();
			time = timeFunctions.getTimeCounter();
			System.out.println("Current time:" + timeFunctions.getSimulationDisplayTime());
			next = (counter == next) ? startBusses(counter) : next;
			moveBusses(time);
			sendETAs(time);
			timeFunctions.simulatorStep();
		}
	}
}
