package com.github.piotrostrow.gameoflife.ui;

import javafx.scene.layout.Region;

public class GridCell extends Region {

	private boolean alive;

	GridCell() {
		setOnMouseClicked(e -> toggle());
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
}
