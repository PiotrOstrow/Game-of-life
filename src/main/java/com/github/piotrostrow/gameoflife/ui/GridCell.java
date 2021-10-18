package com.github.piotrostrow.gameoflife.ui;

import javafx.scene.Node;
import javafx.scene.layout.Region;

public class GridCell {

	private final Region region;

	private final int x;
	private final int y;
	private boolean alive;

	public GridCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.region = new Region();
	}

	public void setAlive(boolean alive) {
		if(this.alive != alive)
			toggle();
	}

	public void toggle() {
		alive = !alive;

		if(alive)
			region.getStyleClass().add("alive");
		else
			region.getStyleClass().remove("alive");
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

	Node asNode() {
		return region;
	}
}
