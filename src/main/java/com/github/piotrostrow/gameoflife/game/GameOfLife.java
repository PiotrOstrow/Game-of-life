package com.github.piotrostrow.gameoflife.game;

public class GameOfLife {

	private final boolean[][] grid;

	public GameOfLife(int width, int height) {
		if(width < 1 || height < 1)
			throw new IllegalArgumentException("Grid area must be positive");

		this.grid = new boolean[width][height];
	}

	public void setCell(int x, int y, boolean alive) {
		grid[x][y] = alive;
	}

	public boolean isCellAlive(int x, int y) {
		return grid[x][y];
	}

	public void calculateNextGeneration() {

	}

	public int getWidth() {
		return grid.length;
	}

	public int getHeight() {
		return grid[0].length;
	}
}
