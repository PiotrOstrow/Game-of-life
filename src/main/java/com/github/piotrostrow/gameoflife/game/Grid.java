package com.github.piotrostrow.gameoflife.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class Grid {

	private final Set<GridPoint> aliveCells = new HashSet<>();

	public void set(Grid other) {
		aliveCells.clear();
		aliveCells.addAll(other.aliveCells);
	}

	public void set(int x, int y, boolean alive) {
		set(new GridPoint(x, y), alive);
	}

	public void set(GridPoint position, boolean alive) {
		Function<GridPoint, Boolean> method = alive ? aliveCells::add : aliveCells::remove;
		method.apply(position);
	}

	public void set(Collection<GridPoint> points, boolean alive) {
		Function<Collection<GridPoint>, Boolean> method = alive ? aliveCells::addAll : aliveCells::removeAll;
		method.apply(points);
	}

	public boolean get(int x, int y) {
		return get(new GridPoint(x, y));
	}

	public boolean get(GridPoint gridPoint) {
		return aliveCells.contains(gridPoint);
	}

	public Set<GridPoint> getAliveCells() {
		return Collections.unmodifiableSet(aliveCells);
	}
}
