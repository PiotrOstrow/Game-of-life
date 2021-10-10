package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import org.junit.jupiter.api.Test;

import static com.github.piotrostrow.gameoflife.TestHelper.assertGridEquals;
import static com.github.piotrostrow.gameoflife.TestHelper.getInputFromFile;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ParserTest {

	@Test
	void testInputWithComments() {
		String input = getInputFromFile("parser_tests/test_comments.txt");
		Parser parser = new Parser(input);
		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife(14, 13);
		expected.setCell(4, 2, true);
		expected.setCell(8, 3, true);
		expected.setCell(3, 4, true);
		expected.setCell(6, 6, true);
		expected.setCell(7, 8, true);
		expected.setCell(8, 8, true);
		expected.setCell(8, 9, true);

		assertGridEquals(actual, expected);
	}

	@Test
	void testInputWithInvalidCharacterBeforeRowShouldIgnoreRowExceptWhitespace() {
		Parser parser = new Parser(getInputFromFile("parser_tests/test_invalid_char_before_row.txt"));
		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife(4, 6);
		expected.setCell(0, 0, true);
		expected.setCell(1, 2, true);
		expected.setCell(3, 3, true);
		expected.setCell(2, 4, true);
		expected.setCell(3, 5, true);

		assertGridEquals(actual, expected);
	}

	@Test
	void testInputWithUnevenDimensionsShouldHaveWidthOfRowWithMaxWidth() {
		Parser parser = new Parser(getInputFromFile("parser_tests/test_uneven_dimensions.txt"));
		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife(20, 4);
		expected.setCell(1, 0, true);
		expected.setCell(1, 1, true);
		expected.setCell(2, 2, true);
		expected.setCell(19, 3, true);

		assertGridEquals(actual, expected);
	}

	@Test
	void testInputWithNoValidRowsShouldThrowIllegalArgumentException() {
		Parser parser = new Parser("no valid rows here\njust some text");
		assertThatThrownBy(parser::parse).isInstanceOf(IllegalArgumentException.class);
	}
}