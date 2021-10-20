package com.github.piotrostrow.gameoflife.game;

import java.util.Objects;

public class GridPoint {

	public final int x;
	public final int y;

	public GridPoint(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		GridPoint gridPoint = (GridPoint) o;
		return x == gridPoint.x && y == gridPoint.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "GridPoint{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
