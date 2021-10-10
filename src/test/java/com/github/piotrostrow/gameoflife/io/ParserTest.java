package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import org.junit.jupiter.api.Test;

import static com.github.piotrostrow.gameoflife.TestHelper.getInputFromFile;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParserTest {

	@Test
	void testInputWithCommentsShouldHaveCorrectDimensions() {
		String input = getInputFromFile("parser_tests/test01.txt");
		Parser parser = new Parser(input);
		GameOfLife actual = parser.parse();

		assertThat(actual.getHeight()).isEqualTo(13);
		assertThat(actual.getWidth()).isEqualTo(14);
	}

	@Test
	void testInputWithInvalidCharacterBeforeRowShouldIgnoreRowExceptWhitespace() {
		Parser parser = new Parser(getInputFromFile("parser_tests/test02.txt"));
		GameOfLife actual = parser.parse();

		assertThat(actual.getWidth()).isEqualTo(4);
		assertThat(actual.getHeight()).isEqualTo(6);
	}

	@Test
	void testInputWithUnevenDimensionsShouldHaveWidthOfRowWithMaxWidth() {
		Parser parser = new Parser(getInputFromFile("parser_tests/test03.txt"));
		GameOfLife actual = parser.parse();

		assertThat(actual.getWidth()).isEqualTo(20);
		assertThat(actual.getHeight()).isEqualTo(4);
	}

	@Test
	void testInputWithNoValidRowsShouldThrowIllegalArgumentException() {
		Parser parser = new Parser("no valid rows here\njust some text");
		assertThatThrownBy(parser::parse).isInstanceOf(IllegalArgumentException.class);
	}
}