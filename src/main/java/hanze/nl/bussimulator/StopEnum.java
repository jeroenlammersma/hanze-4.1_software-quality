package hanze.nl.bussimulator;

public enum StopEnum {
	A(new Position(1, 1)),
	B(new Position(2, 3)),
	C(new Position(4, 4)),
	D(new Position(7, 5)),
	E(new Position(6, 9)),
	F(new Position(11, 11)),
	G(new Position(15, 14)),
	H(new Position(3, 10)),
	I(new Position(19, 8)),
	J(new Position(13, 8)),
	K(new Position(11, 5)),
	L(new Position(12, 2)),
	M(new Position(17, 1)),
	N(new Position(20, 9)),
	O(new Position(19, 6)),
	P(new Position(15, 5)),
	Q(new Position(9, 2)),
	R(new Position(6, 1)),
	S(new Position(5, 22)),
	T(new Position(6, 17)),
	U(new Position(7, 14)),
	V(new Position(8, 11)),
	W(new Position(12, 22)),
	X(new Position(11, 18)),
	Y(new Position(12, 15)),
	Z(new Position(12, 8));

	private final Position position;

	StopEnum(Position position) {
		this.position = position;
	}

	int distance(Position from) {
		return 2 * Math.abs(position.x - from.x) + Math.abs(position.y - from.y);
	}

	Position getPosition() {
		return position;
	}

	static class Position {
		int x;
		int y;

		Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
