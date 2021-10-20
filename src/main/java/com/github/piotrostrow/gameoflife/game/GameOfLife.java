package com.github.piotrostrow.gameoflife.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class GameOfLife {

	public interface Listener {
		void update();
	}
	private final Grid grid;

	private Listener listener;

	public GameOfLife() {
		this.grid = new Grid();
	}

	public void setCells(GameOfLife other) {
		this.grid.set(other.grid);

		if(listener != null)
			listener.update();
	}

	public void setCell(int x, int y, boolean alive) {
		grid.set(x, y, alive);
	}

	public boolean isCellAlive(int x, int y) {
		return grid.get(x, y);
	}

	public void calculateNextGeneration() {
		Map<GridPoint, Integer> neighbourCountByPosition = getNeighbourCountByPosition();
		killCells(neighbourCountByPosition);
		spawnCells(neighbourCountByPosition);

		if(listener != null)
			listener.update();
	}

	private Map<GridPoint, Integer> getNeighbourCountByPosition() {
		Map<GridPoint, Integer> neighbourCountByPosition = new HashMap<>();

		for (GridPoint aliveCell : grid.getAliveCells()) {
			forEachNeighborOf(aliveCell, (x, y) -> {
				GridPoint key = new GridPoint(x, y);
				int currentNeighbourCount = neighbourCountByPosition.getOrDefault(key, 0);
				neighbourCountByPosition.put(key, currentNeighbourCount + 1);
			});
		}

		return neighbourCountByPosition;
	}

	private void forEachNeighborOf(GridPoint point, BiConsumer<Integer, Integer> consumer) {
		for(int x = point.x - 1; x <= point.x + 1; x++) {
			for (int y = point.y - 1; y <= point.y + 1; y++) {
				if(point.x != x || point.y != y) {
					consumer.accept(x, y);
				}
			}
		}
	}

	private void killCells(Map<GridPoint, Integer> neighbourCountByPosition) {
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

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public Set<GridPoint> getAliveCells() {
		return grid.getAliveCells();
	}
}
