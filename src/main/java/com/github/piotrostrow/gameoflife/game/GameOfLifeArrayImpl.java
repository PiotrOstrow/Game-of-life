package com.github.piotrostrow.gameoflife.game;

public class GameOfLifeArrayImpl implements GameOfLife {

	public GameOfLifeArrayImpl(int width, int height) {

	}

	@Override
	public void setCell(int x, int y, boolean alive) {

	}

	@Override
	public boolean isCellAlive(int x, int y) {
		return Math.random() > 0.5;
	}

	@Override
	public void calculateNextGeneration() {

	}
}
