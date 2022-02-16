package hanze.nl.tijdtools;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InfoboardTimeFunctions {

	public Time getCentralTime() {
		try {
			HTTPFunctions httpFuncties = new HTTPFunctions();
			String result = httpFuncties.executeGet("json");
			Time tijd = new ObjectMapper().readValue(result, Time.class);
			return tijd;

		} catch (IOException e) {
			e.printStackTrace();
			return new Time(0, 0, 0);
		}
	}

	public String getFormattedTimeFromCounter(int counter) {
		int uur = counter / 3600;
		int minuten = (counter - 3600 * uur) / 60;
		int seconden = counter - 3600 * uur - 60 * minuten;
		Time tijd = new Time(uur, minuten, seconden);
		return tijd.toString();
	}
}
