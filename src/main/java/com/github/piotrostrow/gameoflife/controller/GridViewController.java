package com.github.piotrostrow.gameoflife.controller;

import com.github.piotrostrow.gameoflife.model.GameOfLife;
import com.github.piotrostrow.gameoflife.view.GridView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import static com.github.piotrostrow.gameoflife.view.GridView.*;

public class GridViewController {

	private static final double SCROLL_FACTOR = 0.025;

	private final GridView gridView;
	private final GameOfLife gameOfLife;

	private double lastMouseDraggedX;
	private double lastMouseDraggedY;

	private boolean wasLastClickedCellAlive;

	public GridViewController(GridView gridView, GameOfLife gameOfLife) {
		this.gridView = gridView;
		this.gameOfLife = gameOfLife;
	}

	public void onMousePressed(MouseEvent event) {
		lastMouseDraggedX = event.getX();
		lastMouseDraggedY = event.getY();

		if (event.getButton() == MouseButton.PRIMARY) {
			toggleCellFromMouseEvent(event);
		}
	}

	public void onMouseDragged(MouseEvent event) {
		if (event.getButton() == MouseButton.SECONDARY) {
			translateFromMouseEvent(event);
		} else if (event.getButton() == MouseButton.PRIMARY) {
			toggleCellFromMouseEvent(event);
		}
	}

	private void translateFromMouseEvent(MouseEvent event) {
		double deltaX = event.getX() - lastMouseDraggedX;
		double deltaY = event.getY() - lastMouseDraggedY;

		lastMouseDraggedX = event.getX();
		lastMouseDraggedY = event.getY();

		gridView.translateBy(deltaX, deltaY);

		gridView.draw();
	}

	public void toggleCellFromMouseEvent(MouseEvent event) {
		double worldX = (event.getX() - gridView.getCanvasXOffset()) / gridView.getScale();
		double worldY = (event.getY() - gridView.getCanvasYOffset()) / gridView.getScale();

		if (worldX < 0)
			worldX -= CELL_SIZE;
		if (worldY < 0)
			worldY -= CELL_SIZE;

		int gridX = (int) (worldX / CELL_SIZE);
		int gridY = (int) (worldY / CELL_SIZE);

		if(event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			wasLastClickedCellAlive = gameOfLife.isCellAlive(gridX, gridY);
		}

		gameOfLife.setCell(gridX, gridY, !wasLastClickedCellAlive);
		gridView.draw();
	}

	public void onScroll(ScrollEvent event) {
		double delta = event.getDeltaY() / Math.abs(event.getDeltaY()) * SCROLL_FACTOR;
		if (Double.isFinite(delta)) {
			double scaleBefore = gridView.getScale();
			double newScale = Math.min(MAX_SCALE, Math.max(MIN_SCALE, scaleBefore + delta));

			gridView.setScale(newScale);

			adjustTranslationToCursorAfterZoom(event, scaleBefore);

			gridView.draw();
		}
	}

	private void adjustTranslationToCursorAfterZoom(ScrollEvent event, double scaleBefore) {
		double scale = gridView.getScale();

		double x = ((event.getX() - gridView.getCanvasXOffset()) / scaleBefore);
		double y = ((event.getY() - gridView.getCanvasYOffset()) / scaleBefore);

		double x2 = ((event.getX() - gridView.getCanvasXOffset()) / scale);
		double y2 = ((event.getY() - gridView.getCanvasYOffset()) / scale);

		gridView.translateBy((x2 - x) * scale, (y2 - y) * scale);
	}
}
