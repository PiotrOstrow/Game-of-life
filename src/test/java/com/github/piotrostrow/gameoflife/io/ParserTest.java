package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTest {

	@Test
	void testInputWithCommentsShouldHaveCorrectDimensions() {
		String input = getInputFromFile("test01.txt");
		Parser parser = new Parser(input);
		GameOfLife actual = parser.parse();

		assertThat(actual.getHeight()).isEqualTo(13);
		assertThat(actual.getWidth()).isEqualTo(14);
	}

	@Test
	void testInputWithInvalidCharacterBeforeRowShouldIgnoreRowExceptWhitespace() {
		Parser parser = new Parser(getInputFromFile("test02.txt"));
		GameOfLife actual = parser.parse();

		assertThat(actual.getWidth()).isEqualTo(4);
		assertThat(actual.getHeight()).isEqualTo(6);
	}

	@Test
	void testInputWithUnevenDimensionsShouldHaveWidthOfRowWithMaxWidth() {
		Parser parser = new Parser(getInputFromFile("test03.txt"));
		GameOfLife actual = parser.parse();

		assertThat(actual.getWidth()).isEqualTo(20);
		assertThat(actual.getHeight()).isEqualTo(4);
	}

	private String getInputFromFile(String fileName) {
		try {
			URL resourceURL = getClass().getClassLoader().getResource("parser_tests/" + fileName);
			assertThat(resourceURL).isNotNull();
			Path path = new File(resourceURL.getFile()).toPath();

			return Files.readString(path);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}