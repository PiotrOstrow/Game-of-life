package com.github.piotrostrow.gameoflife.controller;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.FileUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

public class Controller {

	private final GameOfLife gameOfLife;

	private final Stage stage;

	public Controller(Stage stage, GameOfLife gameOfLife) {
		this.gameOfLife = gameOfLife;
		this.stage = stage;
	}

	public void onNextGen(ActionEvent actionEvent) {
		gameOfLife.calculateNextGeneration();
	}

	public void onLoadGame(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open save file");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file", "*.txt"));
		fileChooser.setInitialDirectory(Paths.get(".").toFile());
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			try {
				GameOfLife game = FileUtils.load(file);
				gameOfLife.setCells(game);
			} catch (RuntimeException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Error loading the file: " + e.getMessage());
				alert.show();
			}
		}
	}

	public void onSaveGame(ActionEvent actionEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save to file");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text file", "*.txt"));
		fileChooser.setInitialDirectory(Paths.get(".").toFile());
		File file = fileChooser.showSaveDialog(stage);

		if (file != null) {
			try {
				FileUtils.save(gameOfLife, file);
			} catch (RuntimeException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Error saving the file: " + e.getMessage());
				alert.show();
			}
		}
	}
}
