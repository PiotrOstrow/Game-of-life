package com.github.piotrostrow.gameoflife.ui;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class GridView {

	private static final Color BACKGROUND_COLOR = Color.LIGHTBLUE;
	private static final Color CELL_COLOR = Color.DARKGRAY;
	private static final Color GRID_COLOR = Color.GRAY;
	private static final int CELL_SIZE = 20;

	private final GameOfLife gameOfLife;

	private final Canvas canvas = new Canvas();
	private final Affine transform = new Affine();

	private double canvasXOffset, canvasYOffset;
	private double mouseDraggedX, mouseDraggedY;

	private double scale = 1.0;

	public GridView(GameOfLife gameOfLife) {
		this.gameOfLife = gameOfLife;

		canvas.widthProperty().addListener(event -> draw());
		canvas.heightProperty().addListener(event -> draw());

		initMouseListeners();

		gameOfLife.setListener(this::draw);

		draw();
	}

	private void initMouseListeners() {
		canvas.setOnMousePressed(this::onMousePressed);
		canvas.setOnMouseDragged(this::onMouseDragged);
		canvas.setOnScroll(this::onScroll);
	}

	private void onMousePressed(MouseEvent event) {
		mouseDraggedX = event.getX();
		mouseDraggedY = event.getY();

		setCellFromMouseEvent(event);
	}

	private void onMouseDragged(MouseEvent event) {
		if (event.getButton() == MouseButton.SECONDARY) {
			double deltaX = event.getX() - mouseDraggedX;
			double deltaY = event.getY() - mouseDraggedY;

			canvasXOffset += deltaX;
			canvasYOffset += deltaY;

			mouseDraggedX = event.getX();
			mouseDraggedY = event.getY();

			draw();
		} else if(event.getButton() == MouseButton.PRIMARY) {
			setCellFromMouseEvent(event);
		}
	}

	private void setCellFromMouseEvent(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY) {
			int x = (int) ((event.getX() - canvasXOffset) / scale) / CELL_SIZE;
			int y = (int) ((event.getY() - canvasYOffset) / scale) / CELL_SIZE;

			gameOfLife.setCell(x, y, event.getButton() == MouseButton.PRIMARY);
			draw();
		}
	}

	private void onScroll(ScrollEvent event) {
		double delta = event.getDeltaY() / Math.abs(event.getDeltaY()) / 10;
		if (!Double.isNaN(delta)) {
			scale = Math.max(0.1, scale + delta);
			scale = Math.min(1.0, scale);

			draw();
		}
	}

	private void draw() {
		GraphicsContext g = canvas.getGraphicsContext2D();

		updateTransform(g);
		clearCanvas(g);
		drawAliveCells(g);
		drawGrid(g);
	}

	private void updateTransform(GraphicsContext g) {
		transform.setToIdentity();
		transform.appendTranslation(canvasXOffset, canvasYOffset);
		transform.appendScale(scale, scale);

		g.setTransform(transform);
	}

	private void clearCanvas(GraphicsContext g) {
		g.setFill(BACKGROUND_COLOR);
		g.fillRect(-canvasXOffset / scale, -canvasYOffset / scale, canvas.getWidth() / scale, canvas.getHeight() / scale);
	}

	private void drawAliveCells(GraphicsContext g) {
		g.setFill(CELL_COLOR);
		gameOfLife.getAliveCells().forEach(point -> g.fillRect(point.x * CELL_SIZE, point.y * CELL_SIZE, CELL_SIZE, CELL_SIZE));
	}

	private void drawGrid(GraphicsContext g) {
		g.setStroke(GRID_COLOR);

		for (int x = (int) -(canvasXOffset / scale / CELL_SIZE); x <= (canvas.getWidth() - canvasXOffset) / scale / CELL_SIZE; x++) {
			g.strokeLine(x * CELL_SIZE, -canvasYOffset / scale, x * CELL_SIZE, (canvas.getHeight() - canvasYOffset) / scale);
		}

		for (int y = (int) -(canvasYOffset / scale / CELL_SIZE); y <= (canvas.getHeight() - canvasYOffset) / scale / CELL_SIZE; y++) {
			g.strokeLine(-canvasXOffset / scale, y * CELL_SIZE, (canvas.getWidth() - canvasXOffset) / scale, y * CELL_SIZE);
		}
	}

	public Node asNode() {
		return new ResizableCanvasContainer(canvas);
	}

}
