package hanze.nl.bussimulator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import hanze.nl.tijdtools.TijdFuncties;

public class Runner {

	private static HashMap<Integer,ArrayList<Bus>> busStart = new HashMap<Integer,ArrayList<Bus>>();
	private static ArrayList<Bus> actieveBussen = new ArrayList<Bus>();
	private static int interval=1000;
	private static int syncInterval=5;
	
	private static void addBus(int starttijd, Bus bus){
		ArrayList<Bus> bussen = new ArrayList<Bus>();
		if (busStart.containsKey(starttijd)) {
			bussen = busStart.get(starttijd);
		}
		bussen.add(bus);
		busStart.put(starttijd,bussen);
		bus.setbusID(starttijd);
	}
	
	private static int startBussen(int tijd){
		for (Bus bus : busStart.get(tijd)){
			actieveBussen.add(bus);
		}
		busStart.remove(tijd);
		return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
	}
	
	public static void moveBussen(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			boolean eindpuntBereikt = bus.move();
			if (eindpuntBereikt) {
				bus.sendLastETA(nu);
				itr.remove();
			}
		}		
	}

	public static void sendETAs(int nu){
		Iterator<Bus> itr = actieveBussen.iterator();
		while (itr.hasNext()) {
			Bus bus = itr.next();
			bus.sendETAs(nu);
		}				
	}

	public static int initBussen(){
		initBuslijn(Lijnen.LIJN1, Bedrijven.ARRIVA, 3);
		initBuslijn(Lijnen.LIJN2, Bedrijven.ARRIVA, 5);
		initBuslijn(Lijnen.LIJN3, Bedrijven.ARRIVA, 4);
		initBuslijn(Lijnen.LIJN4, Bedrijven.FLIXBUS,6);
		initBuslijn(Lijnen.LIJN5, Bedrijven.QBUZZ, 3);
		initBuslijn(Lijnen.LIJN6, Bedrijven.QBUZZ, 5);
		initBuslijn(Lijnen.LIJN7, Bedrijven.ARRIVA, 4);
		initBuslijn(Lijnen.LIJN1, Bedrijven.ARRIVA, 6);
		initBuslijn(Lijnen.LIJN4, Bedrijven.ARRIVA, 12);
		initBuslijn(Lijnen.LIJN5, Bedrijven.FLIXBUS, 10);
		return Collections.min(busStart.keySet());
	}
		
	private static void initBuslijn(Lijnen lijn, Bedrijven bedrijf, int tijd){
		Bus bus1 = new Bus(lijn, bedrijf, 1);
		Bus bus2 = new Bus(lijn, bedrijf, -1);
		addBus(tijd, bus1);
		addBus(tijd, bus2);
	}

	public static void main(String[] args) throws InterruptedException {
		int tijd=0;
		int counter=0;
		TijdFuncties tijdFuncties = new TijdFuncties();
		tijdFuncties.initSimulatorTijden(interval,syncInterval);
		int volgende = initBussen();
		while ((volgende>=0) || !actieveBussen.isEmpty()) {
			counter=tijdFuncties.getCounter();
			tijd=tijdFuncties.getTijdCounter();
			System.out.println("De tijd is:" + tijdFuncties.getSimulatorWeergaveTijd());
			volgende = (counter==volgende) ? startBussen(counter) : volgende;
			moveBussen(tijd);
			sendETAs(tijd);
			tijdFuncties.simulatorStep();
		}
	}
}
