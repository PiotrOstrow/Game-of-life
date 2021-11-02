package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.controller.Controller;
import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.FileUtils;
import com.github.piotrostrow.gameoflife.ui.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		final GameOfLife model = FileUtils.load("acorn.txt");
		final GameView view = new GameView(model);
		final Controller controller = new Controller(stage, model, view);

		view.setupController(controller);

		stage.setScene(new Scene(view.asParent(), 1024, 720));
		stage.initStyle(StageStyle.UNIFIED);
		stage.setTitle("Game of life");
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}