package hanze.nl.bussimulator;

public enum Lines {

	LIJN1(new Stop[] { new Stop(StopEnum.A, 1), new Stop(StopEnum.B, 1), new Stop(StopEnum.C, 1),
			new Stop(StopEnum.D, 1), new Stop(StopEnum.E, 1), new Stop(StopEnum.F, 1), new Stop(StopEnum.G, 1) }),
	LIJN2(new Stop[] { new Stop(StopEnum.H, 1), new Stop(StopEnum.E, 1), new Stop(StopEnum.I, 1),
			new Stop(StopEnum.K, 1), new Stop(StopEnum.L, 1), new Stop(StopEnum.M, 1) }),
	LIJN3(new Stop[] { new Stop(StopEnum.N, 1), new Stop(StopEnum.O, 1), new Stop(StopEnum.P, 1),
			new Stop(StopEnum.K, -1), new Stop(StopEnum.Q, 1), new Stop(StopEnum.R, 1) }),
	LIJN4(new Stop[] { new Stop(StopEnum.S, 1), new Stop(StopEnum.T, 1), new Stop(StopEnum.U, 1),
			new Stop(StopEnum.V, 1), new Stop(StopEnum.I, 1), new Stop(StopEnum.K, 1), new Stop(StopEnum.L, 1),
			new Stop(StopEnum.M, 1) }),
	LIJN5(new Stop[] { new Stop(StopEnum.W, 1), new Stop(StopEnum.X, 1), new Stop(StopEnum.Y, 1),
			new Stop(StopEnum.G, 1),
			new Stop(StopEnum.Z, 1), new Stop(StopEnum.N, 1) }),
	LIJN6(new Stop[] { new Stop(StopEnum.A, 1), new Stop(StopEnum.B, 1), new Stop(StopEnum.H, 1),
			new Stop(StopEnum.T, -1),
			new Stop(StopEnum.X, -1), new Stop(StopEnum.W, -1) }),
	LIJN7(new Stop[] { new Stop(StopEnum.A, 1), new Stop(StopEnum.R, -1), new Stop(StopEnum.Q, -1),
			new Stop(StopEnum.L, 1),
			new Stop(StopEnum.M, 1), new Stop(StopEnum.O, -1), new Stop(StopEnum.N, -1), new Stop(StopEnum.Z, -1),
			new Stop(StopEnum.G, -1), new Stop(StopEnum.Y, -1), new Stop(StopEnum.X, -1), new Stop(StopEnum.W, -1) }),
	LIJN8(new Stop[] { new Stop(StopEnum.M, -1), new Stop(StopEnum.P, 1), new Stop(StopEnum.J, 1),
			new Stop(StopEnum.F, -1),
			new Stop(StopEnum.V, -1), new Stop(StopEnum.E, -1), new Stop(StopEnum.H, -1) });

	private Stop[] stops;

	Lines(Stop... stops) {
		this.stops = stops;
	}

	int getLength() {
		return stops.length;
	}

	StopEnum getStop(int position) {
		return stops[position].stop;
	}

	int getDirection(int position) {
		return stops[position].direction;
	}

	static class Stop {
		StopEnum stop;
		int direction;

		public Stop(StopEnum stop, int direction) {
			this.stop = stop;
			this.direction = direction;
		}
	}
}
