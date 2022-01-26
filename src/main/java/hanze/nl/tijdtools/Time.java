package hanze.nl.tijdtools;

public class Time {
	private int hour;
	private int minute;
	private int second;

	public Time() {
		this.hour = 0;
		this.minute = 0;
		this.second = 0;
	}

	public Time(int hour, int minute, int second) {
		super();
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int seconde) {
		this.second = seconde;
	}

	public void increment(Time step) {
		this.second += step.second;
		this.minute += step.minute;
		this.hour += step.hour;
		if (this.second >= 60) {
			this.second -= 60;
			this.minute++;
		}
		if (this.minute >= 60) {
			this.minute -= 60;
			this.hour++;
		}
	}

	public Time copyTime() {
		return new Time(this.hour, this.minute, this.second);
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", hour, minute, second);
	}
}
