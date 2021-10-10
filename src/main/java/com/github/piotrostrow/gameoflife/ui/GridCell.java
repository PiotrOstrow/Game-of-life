package com.github.piotrostrow.gameoflife.ui;

import javafx.scene.layout.Region;

public class GridCell extends Region {

	private final int x;
	private final int y;
	private boolean alive;

	public GridCell(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setAlive(boolean alive) {
		if(this.alive != alive)
			toggle();
	}

	public void toggle() {
		alive = !alive;

		if(alive)
			getStyleClass().add("alive");
		else
			getStyleClass().remove("alive");
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isAlive() {
		return alive;
	}
}
