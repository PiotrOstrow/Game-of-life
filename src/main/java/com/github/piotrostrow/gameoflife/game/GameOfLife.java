package com.github.piotrostrow.gameoflife.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class GameOfLife {

	private final Grid grid;

	public GameOfLife() {
		this.grid = new Grid();
	}

	public void setCells(GameOfLife other) {
		this.grid.set(other.grid);
	}

	public void setCell(int x, int y, boolean alive) {
		grid.set(x, y, alive);
	}

	public boolean isCellAlive(int x, int y) {
		return grid.get(x, y);
	}

	public void calculateNextGeneration() {
		Map<GridPoint, Integer> neighbourCountByPosition = getNeighbourCountByPosition();
		killDyingCells(neighbourCountByPosition);
		spawnCells(neighbourCountByPosition);
	}

	private Map<GridPoint, Integer> getNeighbourCountByPosition() {
		Map<GridPoint, Integer> neighbourCountByPosition = new HashMap<>();

		for (GridPoint aliveCell : grid.getAliveCells()) {
			forEachNeighborOf(aliveCell, point -> {
				int currentNeighbourCount = neighbourCountByPosition.getOrDefault(point, 0);
				neighbourCountByPosition.put(point, currentNeighbourCount + 1);
			});
		}

		return neighbourCountByPosition;
	}

	private void forEachNeighborOf(GridPoint point, Consumer<GridPoint> consumer) {
		for(long x = Math.max(Integer.MIN_VALUE, point.x - 1L); x <= Math.min(Integer.MAX_VALUE, point.x + 1L); x++) {
			for (long y = Math.max(Integer.MIN_VALUE, point.y - 1L); y <= Math.min(Integer.MAX_VALUE, point.y + 1L); y++) {
				if(point.x != x || point.y != y) {
					consumer.accept(new GridPoint((int) x, (int) y));
				}
			}
		}
	}

	private void killDyingCells(Map<GridPoint, Integer> neighbourCountByPosition) {
		List<GridPoint> dyingCells = grid.getAliveCells().stream()
				.filter(point -> isCellDying(point, neighbourCountByPosition))
				.collect(Collectors.toList());

		grid.set(dyingCells, false);
	}

	private boolean isCellDying(GridPoint point, Map<GridPoint, Integer> neighbourCountByPosition) {
		int neighbourCount = neighbourCountByPosition.getOrDefault(point, 0);
		return neighbourCount < 2 || neighbourCount > 3;
	}

	private void spawnCells(Map<GridPoint, Integer> neighbourCountByPosition) {
		List<GridPoint> spawningCells = neighbourCountByPosition.entrySet().stream()
				.filter(entry -> entry.getValue() == 3)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());

		grid.set(spawningCells, true);
	}

	public Set<GridPoint> getAliveCells() {
		return grid.getAliveCells();
	}
}
