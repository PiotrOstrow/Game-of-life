package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import org.junit.jupiter.api.Test;

import static com.github.piotrostrow.gameoflife.TestHelper.getInputFromFile;
import static org.assertj.core.api.Assertions.assertThat;

class RLEFormatParserTest {

	@Test
	void testInputWithHeaderOneAliveCellAtOrigin() {
		String input = getInputFromFile("parser_tests/RLE/test_one_alive_cell.rle");
		RLEFormatParser parser = new RLEFormatParser(input);

		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife();
		expected.setCell(0, 0, true);

		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testInputWithDeadAndAliveCellsInterleavedInSingleRow() {
		String input = getInputFromFile("parser_tests/RLE/test_dead_and_alive_interleaved.rle");
		RLEFormatParser parser = new RLEFormatParser(input);

		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife();
		for(int i = 0; i < 5; i++)
			expected.setCell(i * 2, 0, true);

		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testInputWithMultipleAliveAndDeadCellsInSequenceWithSomeSpecifiedInRunCount() {
		String input = getInputFromFile("parser_tests/RLE/test_dead_and_alive_interleaved_with_run_count.rle");
		RLEFormatParser parser = new RLEFormatParser(input);

		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife();
		expected.setCell(0, 0, true);
		expected.setCell(1, 0, true);
		expected.setCell(3, 0, true);
		expected.setCell(9, 0, true);
		expected.setCell(20, 0, true);

		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testInputWithMultipleRows() {
		String input = getInputFromFile("parser_tests/RLE/test_input_with_multiple_rows.rle");
		RLEFormatParser parser = new RLEFormatParser(input);

		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife();
		expected.setCell(0, 0, true);
		expected.setCell(1, 1, true);
		expected.setCell(0, 2, true);
		expected.setCell(1, 2, true);
		expected.setCell(4, 2, true);

		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testInputWithRowSeparatedByNewLineCharacter() {
		String input = getInputFromFile("parser_tests/RLE/test_row_with_new_line_character.rle");
		RLEFormatParser parser = new RLEFormatParser(input);

		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife();
		expected.setCell(0, 0, true);
		expected.setCell(2, 0, true);
		expected.setCell(8, 0, true);
		expected.setCell(9, 0, true);

		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testSkipMultipleRows() {
		String input = getInputFromFile("parser_tests/RLE/test_skip_multiple_rows.rle");
		RLEFormatParser parser = new RLEFormatParser(input);

		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife();
		expected.setCell(0, 0, true);
		expected.setCell(0, 3, true);

		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testIgnoreEverythingAfterExclamationMark() {
		String input = getInputFromFile("parser_tests/RLE/test_exclamation_mark.rle");
		RLEFormatParser parser = new RLEFormatParser(input);

		GameOfLife actual = parser.parse();

		GameOfLife expected = new GameOfLife();
		expected.setCell(0, 0, true);

		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}
}