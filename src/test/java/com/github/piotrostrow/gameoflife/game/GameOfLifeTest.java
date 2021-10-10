package com.github.piotrostrow.gameoflife.game;

import com.github.piotrostrow.gameoflife.io.FileLoader;
import org.junit.jupiter.api.Test;

import static com.github.piotrostrow.gameoflife.TestHelper.getFile;
import static org.assertj.core.api.Assertions.assertThat;


class GameOfLifeTest {

	@Test
	void testThreeCellLineShouldOscillate() {
		GameOfLife input = FileLoader.load(getFile("game_tests/test_three_cell_line_input.txt"));
		GameOfLife expected = FileLoader.load(getFile("game_tests/test_three_cell_line_expected.txt"));

		input.calculateNextGeneration();
		assertGridEquality(input, expected);

		input.calculateNextGeneration();
		input.calculateNextGeneration();
		assertGridEquality(input, expected);
	}

	@Test
	void testUnderpopulatedAllShouldDieInNextGeneration() {
		GameOfLife input = FileLoader.load(getFile("game_tests/test_underpopulated_input.txt"));
		GameOfLife expected = FileLoader.load(getFile("game_tests/test_underpopulated_expected.txt"));

		input.calculateNextGeneration();
		assertGridEquality(input, expected);
	}

	@Test
	void testEmptyGridShouldNotSpawnAnyCells() {
		GameOfLife input = FileLoader.load(getFile("game_tests/test_empty_grid.txt"));
		GameOfLife expected = FileLoader.load(getFile("game_tests/test_empty_grid.txt"));

		input.calculateNextGeneration();
		assertGridEquality(input, expected);
	}

	@Test
	void testOverpopulatedAllCellsShouldDieInTwoGenerations() {
		GameOfLife input = FileLoader.load(getFile("game_tests/test_overpopulated_input.txt"));
		GameOfLife expectedGen1 = FileLoader.load(getFile("game_tests/test_overpopulated_expected_gen01.txt"));
		GameOfLife expectedGen2 = FileLoader.load(getFile("game_tests/test_overpopulated_expected_gen02.txt"));

		input.calculateNextGeneration();
		assertGridEquality(input, expectedGen1);

		input.calculateNextGeneration();
		assertGridEquality(input, expectedGen2);
	}

	private void assertGridEquality(GameOfLife input, GameOfLife expected) {
		assertThat(input.getWidth()).isEqualTo(expected.getWidth());
		assertThat(input.getHeight()).isEqualTo(expected.getHeight());

		for (int x = 0; x < input.getWidth(); x++) {
			for (int y = 0; y < input.getHeight(); y++) {
				assertThat(input.isCellAlive(x, y))
						.as("cell %d, %d", x, y)
						.isEqualTo(expected.isCellAlive(x, y));
			}
		}
	}
}