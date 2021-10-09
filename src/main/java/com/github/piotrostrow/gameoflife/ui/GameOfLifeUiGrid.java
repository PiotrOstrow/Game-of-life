package com.github.piotrostrow.gameoflife.ui;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class GameOfLifeUiGrid extends GridPane {

	private final GridCell[][] gridCells;

	public GameOfLifeUiGrid(GameOfLife gameOfLife) {
		this.gridCells = new GridCell[gameOfLife.getWidth()][gameOfLife.getHeight()];

		setAlignment(Pos.CENTER);
		setHgap(1);
		setVgap(1);

		for(int x = 0; x < gameOfLife.getWidth(); x++) {
			for(int y = 0; y < gameOfLife.getHeight(); y++) {
				addGridCell(new GridCell(), x, y);
			}
		}

		update(gameOfLife);
	}

	public void addGridCell(GridCell gridCell, int columnIndex, int rowIndex) {
		super.add(gridCell, columnIndex, rowIndex);
		gridCells[columnIndex][rowIndex] = gridCell;
	}

	public void update(GameOfLife gameOfLife) {
		for(int x = 0; x < getGridWidth(); x++) {
			for(int y = 0; y < getGridHeight(); y++) {
				gridCells[x][y].setAlive(gameOfLife.isCellAlive(x, y));
			}
		}
	}

	private int getGridWidth() {
		return gridCells.length;
	}

	private int getGridHeight() {
		return gridCells[0].length;
	}
}
