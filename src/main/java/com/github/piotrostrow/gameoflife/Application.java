package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.FileUtils;
import com.github.piotrostrow.gameoflife.ui.GameOfLifeUiGrid;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

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

		GameOfLifeUiGrid gameGrid = new GameOfLifeUiGrid(FileUtils.load(new File("test01.txt")));
		Button nextGenerationButton = new Button("Next generation");
		Button loadFileButton = new Button("Load from file");
		Button saveFileButton = new Button("Save to file");

		grid.add(new Label("Game of life"), 0, 0);
		grid.add(gameGrid, 0, 1);
		grid.add(nextGenerationButton, 0, 2);
		grid.add(loadFileButton, 0, 3);
		grid.add(saveFileButton, 0, 4);

		Scene scene = new Scene(grid, 1024, 720);
		scene.getStylesheets().add(Application.class.getResource("style.css").toExternalForm());

		nextGenerationButton.setOnMouseClicked(event -> {
			gameGrid.getGame().calculateNextGeneration();
			gameGrid.update();
		});

		loadFileButton.setOnMouseClicked(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open save file");
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
			fileChooser.setInitialDirectory(Paths.get(".").toFile());
			File file = fileChooser.showOpenDialog(stage);

			if (file != null) {
				try {
					GameOfLife game = FileUtils.load(file);
					gameGrid.setGame(game);
				} catch (RuntimeException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Error loading the file: " + e.getMessage());
					alert.show();
				}
			}
		});

		saveFileButton.setOnMouseClicked(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Save to file");
			fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text file", "*.txt"));
			fileChooser.setInitialDirectory(Paths.get(".").toFile());
			File file = fileChooser.showSaveDialog(stage);

			if (file != null) {
				try {
					FileUtils.save(gameGrid.getGame(), file);
				} catch (RuntimeException e) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Error saving the file: " + e.getMessage());
					alert.show();
				}
			}
		});

		stage.setTitle("Game of life");
		stage.setScene(scene);
	}

	public static void main(String[] args) {
		launch();
	}
}