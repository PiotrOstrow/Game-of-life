package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class TestHelper {

	public static String getInputFromFile(String fileName) {
		try {
			File file = getFile(fileName);
			Path path = file.toPath();

			return Files.readString(path);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static File getFile(String fileName) {
		URL resourceURL = TestHelper.class.getClassLoader().getResource(fileName);
		assertThat(resourceURL).isNotNull();
		return new File(resourceURL.getFile());
	}

	public static void assertGridEquals(GameOfLife actual, GameOfLife expected) {
		assertThat(actual.getWidth()).isEqualTo(expected.getWidth());
		assertThat(actual.getHeight()).isEqualTo(expected.getHeight());

		for (int x = 0; x < actual.getWidth(); x++) {
			for (int y = 0; y < actual.getHeight(); y++) {
				assertThat(actual.isCellAlive(x, y))
						.as("cell %d, %d", x, y)
						.isEqualTo(expected.isCellAlive(x, y));
			}
		}
	}
}
