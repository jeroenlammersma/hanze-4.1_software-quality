package hanze.nl.bussimulator;

import com.thoughtworks.xstream.XStream;
import hanze.nl.bussimulator.StopEnum.Position;

public class BusMessageControler {

    private Companies company;
    private Lines line;
    private int stopNumber;
    private int untilNextStop;
    private int direction;
    private boolean atStop;
    private String busID;

    public BusMessageControler(Companies company, Lines line, int stopNumber, int untilNextStop, int direction,
            boolean atStop, String busID) {
        this.company = company;
        this.line = line;
        this.stopNumber = stopNumber;
        this.untilNextStop = untilNextStop;
        this.direction = direction;
        this.atStop = atStop;
        this.busID = busID;
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
