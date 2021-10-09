package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.FileLoader;
import com.github.piotrostrow.gameoflife.ui.GameOfLifeUiGrid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;

public class Application extends javafx.application.Application {

	@Override
	public void start(Stage stage) {
		constructScene(stage);
		stage.show();
	}

	static void constructScene(Stage stage) {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));
		grid.setAlignment(Pos.CENTER);

		GameOfLife gameOfLife = FileLoader.load(new File("test01.txt"));

		GameOfLifeUiGrid gameGrid = new GameOfLifeUiGrid(gameOfLife);
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

		scene.getStylesheets().add(Application.class.getResource("style.css").toExternalForm());
		stage.setTitle("Game of life");
		stage.setScene(scene);
	}

	public static void main(String[] args) {
		launch();
	}
}