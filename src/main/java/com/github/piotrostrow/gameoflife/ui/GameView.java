package com.github.piotrostrow.gameoflife.ui;

import com.github.piotrostrow.gameoflife.controller.Controller;
import com.github.piotrostrow.gameoflife.game.GameOfLife;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;

public class GameView {

	private GridPane parent;

	private Button nextGenerationButton;
	private Button loadFileButton;
	private Button saveFileButton;
	private Button playButton;

	private Slider speedSlider;

	public GameView(GameOfLife game) {
		initView(game);
	}

	private void initView(GameOfLife game) {
		parent = new GridPane();
		parent.setHgap(10);
		parent.setVgap(10);
		parent.setPadding(new Insets(20));
		parent.setAlignment(Pos.CENTER);

		GridView gridView = new GridView(game);

		nextGenerationButton = new Button("Next generation");
		loadFileButton = new Button("Load from file");
		saveFileButton = new Button("Save to file");
		playButton = new Button("Play");

		speedSlider = new Slider(1, 60, 1);

		parent.add(new Label("Game of life"), 0, 0);
		parent.add(gridView.asNode(), 0, 1);
		parent.add(nextGenerationButton, 0, 2);
		parent.add(loadFileButton, 0, 3);
		parent.add(saveFileButton, 0, 4);
		parent.add(playButton, 0, 5);
		parent.add(speedSlider, 0, 6);
	}

	public void setupController(Controller controller) {
		nextGenerationButton.setOnAction(controller::onNextGen);
		loadFileButton.setOnAction(controller::onLoadGame);
		saveFileButton.setOnAction(controller::onSaveGame);
		playButton.setOnAction(controller::onPlay);
		speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> controller.onChangeSpeed(newValue));
	}

	public void togglePlayButton() {
		if (playButton.getText().equalsIgnoreCase("Play")) {
			playButton.setText("Pause");
		} else {
			playButton.setText("Play");
		}
	}

	public Parent asParent() {
		return parent;
	}
}
