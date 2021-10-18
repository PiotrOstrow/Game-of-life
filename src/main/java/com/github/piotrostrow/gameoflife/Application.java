package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.controller.Controller;
import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.FileUtils;
import com.github.piotrostrow.gameoflife.ui.GameView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Application extends javafx.application.Application {

	@Override
	public void start(Stage stage) {
		final GameOfLife model = FileUtils.load("acorn.txt");
		final GameView view = new GameView(model);
		final Controller controller = new Controller(stage, model, view);

		view.setupController(controller);

		final Scene scene = new Scene(view.asParent(), 1024, 720);
		addCss(scene);

		stage.setScene(scene);
		stage.initStyle(StageStyle.UNIFIED);
		stage.setTitle("Game of life");
		stage.show();
	}

	private void addCss(Scene scene) {
		URL resource = Application.class.getResource("style.css");
		if (resource == null) {
			throw new RuntimeException("Could not load style.css");
		}

		scene.getStylesheets().add(resource.toExternalForm());
	}

	public static void main(String[] args) {
		launch();
	}
}