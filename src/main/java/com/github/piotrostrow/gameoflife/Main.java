package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.controller.Controller;
import com.github.piotrostrow.gameoflife.controller.GridViewController;
import com.github.piotrostrow.gameoflife.io.FileUtils;
import com.github.piotrostrow.gameoflife.model.GameOfLife;
import com.github.piotrostrow.gameoflife.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		final GameOfLife model = FileUtils.load("acorn.txt");
		final MainView view = new MainView(model);
		final Controller controller = new Controller(stage, model, view);
		final GridViewController gridViewController = new GridViewController(view.getGridView(), model);

		view.setupControllers(controller, gridViewController);

		stage.setScene(new Scene(view.asParent(), 1024, 720));
		stage.initStyle(StageStyle.UNIFIED);
		stage.setTitle("Game of life");
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}