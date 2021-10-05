module com.github.piotrostrow.gameoflife {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.web;

	requires org.controlsfx.controls;
	requires org.kordamp.ikonli.javafx;
	requires eu.hansolo.tilesfx;

	opens com.github.piotrostrow.gameoflife to javafx.fxml;
	exports com.github.piotrostrow.gameoflife;
	exports com.github.piotrostrow.gameoflife.ui;
	opens com.github.piotrostrow.gameoflife.ui to javafx.fxml;
	exports com.github.piotrostrow.gameoflife.game;
	opens com.github.piotrostrow.gameoflife.game to javafx.fxml;
}