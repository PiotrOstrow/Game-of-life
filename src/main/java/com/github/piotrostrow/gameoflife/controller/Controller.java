package com.github.piotrostrow.gameoflife.controller;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.FileFormat;
import com.github.piotrostrow.gameoflife.io.FileUtils;
import com.github.piotrostrow.gameoflife.ui.GameView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

	private final Stage stage;
	private final GameOfLife gameOfLife;
	private final GameView gameView;

	private final Timeline autoPlayTimeline;

	public Controller(Stage stage, GameOfLife gameOfLife, GameView gameView) {
		this.stage = stage;
		this.gameOfLife = gameOfLife;
		this.gameView = gameView;

		this.autoPlayTimeline = new Timeline(new KeyFrame(Duration.millis(500), e -> gameOfLife.calculateNextGeneration()));
		this.autoPlayTimeline.setCycleCount(Timeline.INDEFINITE);
	}

	public void onNextGen(ActionEvent actionEvent) {
		gameOfLife.calculateNextGeneration();
	}

	public void onLoadGame(ActionEvent actionEvent) {
		pauseIfAutoPlaying();

		File file = newFileChooser("Open save file").showOpenDialog(stage);

		if (file != null) {
			try {
				GameOfLife loadedGame = FileUtils.load(file);
				gameOfLife.setCells(loadedGame);
			} catch (RuntimeException e) {
				showErrorAlert("Error loading the file: " + e.getMessage());
			}
		}
	}

	public void onSaveGame(ActionEvent actionEvent) {
		pauseIfAutoPlaying();

		File file = newFileChooser("Save to file").showSaveDialog(stage);

		if (file != null) {
			try {
				FileUtils.save(gameOfLife, file);
			} catch (RuntimeException e) {
				showErrorAlert("Error saving the file: " + e.getMessage());
			}
		}
	}

	private void pauseIfAutoPlaying() {
		if(autoPlayTimeline.getStatus() == Animation.Status.RUNNING) {
			autoPlayTimeline.stop();
			gameView.togglePlayButton();
		}
	}

	private FileChooser newFileChooser(String title) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(getExtensionFilters());
		fileChooser.setInitialDirectory(Paths.get(".").toFile());
		fileChooser.setTitle(title);
		return fileChooser;
	}

	private FileChooser.ExtensionFilter[] getExtensionFilters() {
		List<FileChooser.ExtensionFilter> filters = Arrays.stream(FileFormat.values())
				.map(e -> new FileChooser.ExtensionFilter(e.getDescription(), "*." + e.getExtension()))
				.collect(Collectors.toList());

		String[] allFileExtensions = Arrays.stream(FileFormat.values())
				.map(e -> "*." + e.getExtension())
				.toArray(String[]::new);
		filters.add(0, new FileChooser.ExtensionFilter("Supported formats", allFileExtensions));

		return filters.toArray(new FileChooser.ExtensionFilter[0]);
	}

	private void showErrorAlert(String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(message);
		alert.show();
	}

	public void onPlay(ActionEvent actionEvent) {
		if(autoPlayTimeline.getStatus() != Animation.Status.RUNNING) {
			autoPlayTimeline.play();
		} else {
			autoPlayTimeline.stop();
		}

		gameView.togglePlayButton();
	}

	public void onChangeSpeed(Number value) {
		autoPlayTimeline.setRate(value.doubleValue());
	}
}
