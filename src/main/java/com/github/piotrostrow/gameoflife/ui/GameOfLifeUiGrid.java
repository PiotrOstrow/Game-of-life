package com.github.piotrostrow.gameoflife.ui;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class GameOfLifeUiGrid extends GridPane {

	private GameOfLife gameOfLife;
	private GridCell[][] gridCells;

	public GameOfLifeUiGrid(GameOfLife gameOfLife) {
		setAlignment(Pos.CENTER);
		setHgap(1);
		setVgap(1);

		setGame(gameOfLife);

		update();
	}

	public void addGridCell(GridCell gridCell, int columnIndex, int rowIndex) {
		super.add(gridCell, columnIndex, rowIndex);
		gridCells[columnIndex][rowIndex] = gridCell;
	}

	public void setGame(GameOfLife gameOfLife) {
		this.gameOfLife = gameOfLife;
		this.gridCells = new GridCell[gameOfLife.getWidth()][gameOfLife.getHeight()];

		getChildren().clear();

		for(int x = 0; x < gameOfLife.getWidth(); x++) {
			for(int y = 0; y < gameOfLife.getHeight(); y++) {
				GridCell gridCell = new GridCell(x, y);
				gridCell.setOnMouseClicked(e -> onCellClick(gridCell));
				addGridCell(gridCell, x, y);
			}
		}

		update();
	}

	public void update() {
		for(int x = 0; x < getGridWidth(); x++) {
			for(int y = 0; y < getGridHeight(); y++) {
				gridCells[x][y].setAlive(gameOfLife.isCellAlive(x, y));
			}
		}
	}

	private void onCellClick(GridCell cell) {
		cell.toggle();
		gameOfLife.setCell(cell.getX(), cell.getY(), cell.isAlive());
	}

	private int getGridWidth() {
		return gridCells.length;
	}

	private int getGridHeight() {
		return gridCells[0].length;
	}

	public GameOfLife getGame() {
		return gameOfLife;
	}
}
