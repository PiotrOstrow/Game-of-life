package com.github.piotrostrow.gameoflife.game;

public interface GameOfLife {

	void setCell(int x, int y, boolean alive);

	boolean isCellAlive(int x, int y);

	void calculateNextGeneration();
}
