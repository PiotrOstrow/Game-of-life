package com.github.piotrostrow.gameoflife.game;

import java.util.Arrays;

public class GameOfLife {

	public interface Listener {
		void update();
	}

	private boolean[][] grid;
	private boolean[][] nextGenGrid;

	private Listener listener;

	public GameOfLife(int width, int height) {
		if(width < 1 || height < 1)
			throw new IllegalArgumentException("Grid area must be positive");

		this.grid = new boolean[width][height];
		this.nextGenGrid = new boolean[width][height];
	}

	public void setCells(GameOfLife other) {
		this.grid = Arrays.stream(other.grid).map(boolean[]::clone).toArray(boolean[][]::new);

		if(listener != null)
			listener.update();
	}

	public void setCell(int x, int y, boolean alive) {
		grid[x][y] = alive;
	}

	public boolean isCellAlive(int x, int y) {
		return grid[x][y];
	}

	public void calculateNextGeneration() {
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				calculateNextGenCell(x, y);
			}
		}

		swapGrids();

		if(listener != null)
			listener.update();
	}

	private void calculateNextGenCell(int x, int y) {
		int neighbourCount = getNeighbourCount(x, y);
		if(isCellAlive(x, y)) {
			nextGenGrid[x][y] = neighbourCount == 2 || neighbourCount == 3;
		} else {
			nextGenGrid[x][y] = neighbourCount == 3;
		}
	}

	private int getNeighbourCount(int x, int y) {
		int neighbourCount = 0;

		// TODO: move the min max logic to isCellAlive
		for(int x1 = Math.max(0, x - 1); x1 <= Math.min(x + 1, getWidth() - 1); x1++) {
			for(int y1 = Math.max(0, y - 1); y1 <= Math.min(y + 1, getHeight() - 1); y1++) {
				if(!(x1 == x && y1 == y) && isCellAlive(x1, y1))
					neighbourCount++;
			}
		}

		return neighbourCount;
	}

	private void swapGrids() {
		boolean[][] temp = grid;
		grid = nextGenGrid;
		nextGenGrid = temp;
	}

	public int getWidth() {
		return grid.length;
	}

	public int getHeight() {
		return grid[0].length;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}
}
