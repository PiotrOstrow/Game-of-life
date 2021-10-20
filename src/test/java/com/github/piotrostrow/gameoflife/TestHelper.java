package com.github.piotrostrow.gameoflife;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.FileUtils;

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

	public static GameOfLife loadFromFile(String fileName) {
		return FileUtils.load(getFile(fileName));
	}

	public static File getFile(String fileName) {
		URL resourceURL = TestHelper.class.getClassLoader().getResource(fileName);
		assertThat(resourceURL).isNotNull();
		return new File(resourceURL.getFile());
	}
}
