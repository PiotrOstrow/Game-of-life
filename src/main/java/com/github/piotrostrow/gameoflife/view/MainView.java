package com.github.piotrostrow.gameoflife.view;

import com.github.piotrostrow.gameoflife.controller.Controller;
import com.github.piotrostrow.gameoflife.controller.GridViewController;
import com.github.piotrostrow.gameoflife.model.GameOfLife;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class MainView {

	private final BorderPane parent = new BorderPane();

	private final GridView gridView;

	private Button nextGenerationButton;
	private Button loadFileButton;
	private Button saveFileButton;
	private Button playButton;

	private Slider speedSlider;

	public MainView(GameOfLife game) {
		gridView = new GridView(game);

		parent.setTop(createTopPane());
		parent.setCenter(gridView.asNode());
		parent.setBottom(createBottomPane());
	}

	private GridPane createTopPane() {
		GridPane topGridPane = createNewGridPane();

		nextGenerationButton = new Button("Next generation");
		loadFileButton = new Button("Load from file");
		saveFileButton = new Button("Save to file");
		playButton = new Button("Play");

		topGridPane.add(nextGenerationButton, 0, 0);
		topGridPane.add(playButton, 1, 0);
		topGridPane.add(loadFileButton, 2, 0);
		topGridPane.add(saveFileButton, 3, 0);

		return topGridPane;
	}

	private GridPane createBottomPane() {
		GridPane bottomGridPane = createNewGridPane();

		speedSlider = new Slider(1, 60, 1);
		speedSlider.setPrefWidth(500);

		Label sliderLabel = new Label("Autoplay speed");
		GridPane.setHalignment(sliderLabel, HPos.CENTER);

		bottomGridPane.add(sliderLabel, 0, 0);
		bottomGridPane.add(speedSlider, 0, 1);
		return bottomGridPane;
	}

	private GridPane createNewGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(20));
		gridPane.setAlignment(Pos.CENTER);
		return gridPane;
	}

	public void setupControllers(Controller controller, GridViewController gridViewController) {
		setupController(controller);
		gridView.setupController(gridViewController);
	}

	private void setupController(Controller controller) {
		nextGenerationButton.setOnAction(event -> controller.onNextGen());
		loadFileButton.setOnAction(event -> controller.onLoadGame());
		saveFileButton.setOnAction(event -> controller.onSaveGame());
		playButton.setOnAction(event -> controller.onPlay());
		speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> controller.onChangeSpeed(newValue));
	}

	public void draw() {
		gridView.draw();
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

	public GridView getGridView() {
		return gridView;
	}
}
