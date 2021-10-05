package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.game.GameOfLifeArrayImpl;
import com.github.piotrostrow.gameoflife.ui.GameOfLifeUiGrid;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class HelloApplication extends Application {

	@Override
	public void start(Stage stage) {
		constructScene(stage);
		stage.show();
	}

	static void constructScene(Stage stage) {
		final int WIDTH = 15;
		final int HEIGHT = 15;

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));
		grid.setAlignment(Pos.CENTER);

		GameOfLife gameOfLife = new GameOfLifeArrayImpl(WIDTH, HEIGHT);

		GameOfLifeUiGrid gameGrid = new GameOfLifeUiGrid(WIDTH, HEIGHT);
		Button nextGenerationButton = new Button("Next generation");

		grid.add(new Label("Game of life"), 0, 0);
		grid.add(gameGrid, 0, 1);
		grid.add(nextGenerationButton, 0, 2);

		Scene scene = new Scene(grid, 1024, 720);

		scene.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.F5) {
				constructScene(stage);
			}
		});

		nextGenerationButton.setOnMouseClicked(event -> {
			gameOfLife.calculateNextGeneration();
			gameGrid.update(gameOfLife);
		});

		scene.getStylesheets().add(HelloApplication.class.getResource("style.css").toExternalForm());
		stage.setTitle("Game of life");
		stage.setScene(scene);
	}

	public static void main(String[] args) {
		launch();
	}
}