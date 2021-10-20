package com.github.piotrostrow.gameoflife.ui;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridView {

	private final GameOfLife gameOfLife;

	private final GridPane gridPane;
	private final GridCell[][] gridCells;

	public GridView(GameOfLife gameOfLife) {
		this.gameOfLife = gameOfLife;
		this.gridPane = new GridPane();
		this.gridCells = new GridCell[39][21];

		initGrid(gameOfLife);

		gameOfLife.setListener(this::update);
	}

	private void initGrid(GameOfLife gameOfLife) {
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(1);
		gridPane.setVgap(1);

		for(int x = 0; x < getGridWidth(); x++) {
			for(int y = 0; y < getGridHeight(); y++) {
				GridCell gridCell = new GridCell(x, y);
				gridCell.asNode().setOnMouseClicked(e -> onCellClick(gridCell));
				addGridCell(gridCell, x, y);
			}
		}

		update();
	}

	private void addGridCell(GridCell gridCell, int columnIndex, int rowIndex) {
		gridPane.add(gridCell.asNode(), columnIndex, rowIndex);
		gridCells[columnIndex][rowIndex] = gridCell;
	}

	private void onCellClick(GridCell cell) {
		cell.toggle();
		gameOfLife.setCell(cell.getX(), cell.getY(), cell.isAlive());
	}

	public void update() {
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

	public GameOfLife getGame() {
		return gameOfLife;
	}

	public Node asNode() {
		return gridPane;
	}
}
