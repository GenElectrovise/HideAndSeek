package net.clicksminuteper.HideAndSeek.main.game;

public class ThreeDCoordinate {
	public double x;
	public double y;
	public double z;

	public ThreeDCoordinate(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public String toString() {
		return "{" + x + "," + y + "," + z + "}";
	}
}
