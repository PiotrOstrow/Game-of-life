package com.github.piotrostrow.gameoflife.game;

import com.github.piotrostrow.gameoflife.io.FileUtils;
import org.junit.jupiter.api.Test;

import static com.github.piotrostrow.gameoflife.TestHelper.assertGridEquals;
import static com.github.piotrostrow.gameoflife.TestHelper.getFile;

class GameOfLifeTest {

	@Test
	void testThreeCellLineShouldOscillate() {
		GameOfLife input = FileUtils.load(getFile("game_tests/test_three_cell_line_input.txt"));
		GameOfLife expected = FileUtils.load(getFile("game_tests/test_three_cell_line_expected.txt"));

		input.calculateNextGeneration();
		assertGridEquals(input, expected);

		input.calculateNextGeneration();
		input.calculateNextGeneration();
		assertGridEquals(input, expected);
	}

	@Test
	void testUnderpopulatedAllShouldDieInNextGeneration() {
		GameOfLife input = FileUtils.load(getFile("game_tests/test_underpopulated_input.txt"));
		GameOfLife expected = FileUtils.load(getFile("game_tests/test_underpopulated_expected.txt"));

		input.calculateNextGeneration();
		assertGridEquals(input, expected);
	}

	@Test
	void testEmptyGridShouldNotSpawnAnyCells() {
		GameOfLife input = FileUtils.load(getFile("game_tests/test_empty_grid.txt"));
		GameOfLife expected = FileUtils.load(getFile("game_tests/test_empty_grid.txt"));

		input.calculateNextGeneration();
		assertGridEquals(input, expected);
	}

	@Test
	void testOverpopulatedAllCellsShouldDieInTwoGenerations() {
		GameOfLife input = FileUtils.load(getFile("game_tests/test_overpopulated_input.txt"));
		GameOfLife expectedGen1 = FileUtils.load(getFile("game_tests/test_overpopulated_expected_gen01.txt"));
		GameOfLife expectedGen2 = FileUtils.load(getFile("game_tests/test_overpopulated_expected_gen02.txt"));

		input.calculateNextGeneration();
		assertGridEquals(input, expectedGen1);

		input.calculateNextGeneration();
		assertGridEquals(input, expectedGen2);
	}
}