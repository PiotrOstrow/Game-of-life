package com.github.piotrostrow.gameoflife.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;

/**
 * Pane container with overridden layout method to make the canvas resizable, which it is not by default
 * http://fxexperience.com/2014/05/resizable-grid-using-canvas/
 */
class ResizableCanvasContainer extends StackPane {
	private final Canvas canvas;

	ResizableCanvasContainer(Canvas canvas) {
		super(canvas);
		this.canvas = canvas;
	}

	@Override
	protected void layoutChildren() {
		double width = (getWidth() - snappedLeftInset() - snappedRightInset());
		double height = (getHeight() - snappedTopInset() - snappedBottomInset());
		canvas.setLayoutX(snappedLeftInset());
		canvas.setLayoutY(snappedTopInset());
		if (width != canvas.getWidth() || height != canvas.getHeight()) {
			canvas.setWidth(width);
			canvas.setHeight(height);
		}
	}
}
