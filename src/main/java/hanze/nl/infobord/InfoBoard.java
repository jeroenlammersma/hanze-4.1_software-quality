package hanze.nl.infobord;

import java.io.IOException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Insets;
import com.fasterxml.jackson.databind.ObjectMapper;
import hanze.nl.tijdtools.InfoboardTimeFunctions;

public class InfoBoard {

	private static HashMap<String, Integer> lastMessage = new HashMap<String, Integer>();
	private static HashMap<String, JSONMessage> infoBoardLines = new HashMap<String, JSONMessage>();
	private static InfoBoard infoBoard;
	private static int hashValue;
	private JFrame frame;
	private JLabel timelinel1;
	private JLabel timelinel2;
	private JLabel line1;
	private JLabel line2;
	private JLabel line3;
	private JLabel line4;

	private InfoBoard() {
		this.frame = new JFrame("InfoBoard");
		JPanel panel = new JPanel();
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		panel.setBorder(new EmptyBorder(new Insets(10, 20, 10, 20)));
		this.timelinel1 = new JLabel("Screen last updated at: ");
		this.timelinel2 = new JLabel("00:00:00");
		this.line1 = new JLabel("-line1-");
		this.line2 = new JLabel("-line2-");
		this.line3 = new JLabel("-line3-");
		this.line4 = new JLabel("-line4-");
		panel.add(timelinel1);
		panel.add(timelinel2);
		panel.add(line1);
		panel.add(line2);
		panel.add(line3);
		panel.add(line4);
		hashValue = 0;
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}

	public static InfoBoard getInfoBoard() {
		if (infoBoard == null) {
			infoBoard = new InfoBoard();
		}
		return infoBoard;
	}

	public static void processedMessage(String incoming) {
		try {
			JSONMessage message = new ObjectMapper().readValue(incoming, JSONMessage.class);
			String busID = message.busID;
			Integer time = message.time;
			if (!lastMessage.containsKey(busID) || lastMessage.get(busID) <= time) {
				lastMessage.put(busID, time);
				if (message.arrivalTime == 0) {
					infoBoardLines.remove(busID);
				} else {
					infoBoardLines.put(busID, message);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setLines() {
		String[] infoText = { "--1--", "--2--", "--3--", "--4--", "empty" };
		int[] arrivalTimes = new int[5];
		int numberOfLines = 0;

		if (!infoBoardLines.isEmpty())
			sortLines(infoText, numberOfLines, arrivalTimes);

		doCheckRepaint(infoText, numberOfLines, arrivalTimes);
	}

	private void sortLines(String[] infoText, int numberOfLines, int[] arrivalTimes) {
		for (String busID : infoBoardLines.keySet()) {
			JSONMessage line = infoBoardLines.get(busID);
			int thisTime = line.arrivalTime;
			String thisText = line.GetInfoLine();
			int place = numberOfLines;

			for (int i = numberOfLines; i > -1; i--) {
				if (thisTime < arrivalTimes[i - 2]) {
					arrivalTimes[i] = arrivalTimes[i - 2];
					infoText[i] = infoText[i - 2];
					place = i - 2;
				}
			}

			arrivalTimes[place] = thisTime;
			infoText[place] = thisText;

			if (numberOfLines < 3)
				numberOfLines++;
		}
	}

	private boolean checkRepaint(int numberOfLines, int[] arrivalTimes) {
		int totalNumberOfTimes = 0;
		for (int i = 0; i < numberOfLines; i++) {
			totalNumberOfTimes += arrivalTimes[i];
		}
		if (hashValue != totalNumberOfTimes) {
			hashValue = totalNumberOfTimes;
			return true;
		}
		return false;
	}

	private void repaintInfoBord(String[] infoText) {
		InfoboardTimeFunctions timeFunctions = new InfoboardTimeFunctions();
		String tijd = timeFunctions.getCentralTime().toString();
		timelinel2.setText(tijd);
		line1.setText(infoText[0]);
		line2.setText(infoText[1]);
		line3.setText(infoText[2]);
		line4.setText(infoText[3]);
		frame.repaint();
	}

	private void doCheckRepaint(String[] infoText, int numberOfLines, int[] arrivalTimes) {
		if (checkRepaint(numberOfLines, arrivalTimes))
			repaintInfoBord(infoText);
	}

}
