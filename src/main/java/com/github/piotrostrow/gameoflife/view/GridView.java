package com.github.piotrostrow.gameoflife.view;

import com.github.piotrostrow.gameoflife.controller.GridViewController;
import com.github.piotrostrow.gameoflife.model.GameOfLife;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class GridView {

	private static final Color BACKGROUND_COLOR = Color.LIGHTBLUE;
	private static final Color CELL_COLOR = Color.DARKGRAY;
	private static final Color GRID_COLOR = Color.GRAY;

	public static final double CELL_SIZE = 20.0;
	public static final double MIN_SCALE = 0.1;
	public static final double MAX_SCALE = 1.0;

	private final GameOfLife gameOfLife;

	private final Canvas canvas = new Canvas();
	private final Affine transform = new Affine();

	private double canvasXOffset;
	private double canvasYOffset;

	private double scale = 1.0;

	public GridView(GameOfLife gameOfLife) {
		this.gameOfLife = gameOfLife;

		canvas.widthProperty().addListener(event -> draw());
		canvas.heightProperty().addListener(event -> draw());

		draw();
	}

	public void draw() {
		GraphicsContext g = canvas.getGraphicsContext2D();

		updateTransform(g);
		clearCanvas(g);
		drawAliveCells(g);
		drawGrid(g);
		drawAxes(g);
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

	private void drawAxes(GraphicsContext g) {
		g.setStroke(Color.BLACK);
		g.strokeLine(0, -canvasYOffset / scale, 0, (canvas.getHeight() - canvasYOffset) / scale);
		g.strokeLine(-canvasXOffset / scale, 0, (canvas.getWidth() - canvasXOffset) / scale, 0);
	}

	public void setupController(GridViewController gridViewController) {
		canvas.setOnMousePressed(gridViewController::onMousePressed);
		canvas.setOnMouseDragged(gridViewController::onMouseDragged);
		canvas.setOnScroll(gridViewController::onScroll);
	}

	public double getCanvasXOffset() {
		return canvasXOffset;
	}

	public double getCanvasYOffset() {
		return canvasYOffset;
	}

	public void translateBy(double x, double y) {
		this.canvasXOffset += x;
		this.canvasYOffset += y;
	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Node asNode() {
		return new ResizableCanvasContainer(canvas);
	}
}
