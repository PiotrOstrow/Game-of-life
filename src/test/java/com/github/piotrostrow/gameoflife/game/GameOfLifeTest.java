package com.github.piotrostrow.gameoflife.game;

import org.junit.jupiter.api.Test;

import static com.github.piotrostrow.gameoflife.TestHelper.loadFromFile;
import static org.assertj.core.api.Assertions.assertThat;


class GameOfLifeTest {

	@Test
	void testAliveCellWithThreeNeighboursShouldStayAlive() {
		GameOfLife actual = loadFromFile("game_tests/test_alive_with_three_neighbours.txt");

		actual.calculateNextGeneration();
		assertThat(actual.isCellAlive(3, 1)).isTrue();
	}

	@Test
	void testDeadCellWithThreeNeighboursShouldBecomeAlive() {
		GameOfLife actual = loadFromFile("game_tests/test_dead_cell_with_three_neighbours_gen00.txt");
		GameOfLife expectedGen1 = loadFromFile("game_tests/test_dead_cell_with_three_neighbours_gen01.txt");

		actual.calculateNextGeneration();
		assertThat(actual.getAliveCells()).isEqualTo(expectedGen1.getAliveCells());
	}

	@Test
	void testAliveCellsWithMoreThanThreeNeighboursShouldDie() {
		GameOfLife actual = loadFromFile("game_tests/test_alive_with_more_than_three_neighbours.txt");

		actual.calculateNextGeneration();
		assertThat(actual.isCellAlive(1, 1)).isFalse();
	}

	@Test
	void testThreeCellLineShouldOscillate() {
		GameOfLife actual = loadFromFile("game_tests/test_three_cell_line_input.txt");
		GameOfLife expected = loadFromFile("game_tests/test_three_cell_line_expected.txt");

		actual.calculateNextGeneration();
		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());

		actual.calculateNextGeneration();
		actual.calculateNextGeneration();
		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testUnderpopulatedCellsAllShouldDieInNextGeneration() {
		GameOfLife actual = loadFromFile("game_tests/test_underpopulated_input.txt");
		GameOfLife expected = loadFromFile("game_tests/test_underpopulated_expected.txt");

		actual.calculateNextGeneration();
		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}

	@Test
	void testEmptyGridShouldNotSpawnAnyCells() {
		GameOfLife actual = loadFromFile("game_tests/test_empty_grid.txt");
		GameOfLife expected = loadFromFile("game_tests/test_empty_grid.txt");

		actual.calculateNextGeneration();
		assertThat(actual.getAliveCells()).isEqualTo(expected.getAliveCells());
	}
}