package com.github.piotrostrow.gameoflife.game;

import org.junit.jupiter.api.Test;

import static com.github.piotrostrow.gameoflife.TestHelper.loadFromFile;
import static org.assertj.core.api.Assertions.assertThat;


class GameOfLifeTest {

	@Test
	void testEmptyGridShouldNotSpawnAnyCells() {
		GameOfLife actual = new GameOfLife();

		actual.calculateNextGeneration();
		assertThat(actual.getAliveCells()).isEmpty();
	}

	@Test
	void testOneAliveCellWithNoNeighboursShouldDie() {
		GameOfLife actual = new GameOfLife();
		actual.setCell(2, 2, true);
		actual.calculateNextGeneration();

		assertThat(actual.getAliveCells()).isEmpty();
	}

	@Test
	void testMultipleAliveCellsWithNoNeighboursAllShouldDie() {
		GameOfLife actual = loadFromFile("game_tests/test_underpopulated_input.txt");

		actual.calculateNextGeneration();
		assertThat(actual.getAliveCells()).isEmpty();
	}

	@Test
	void testAliveCellWithTwoNeighboursShouldStayAlive() {
		GameOfLife actual = new GameOfLife();
		actual.setCell(1, 1, true);
		actual.setCell(1, 0, true);
		actual.setCell(2, 1, true);

		actual.calculateNextGeneration();

		assertThat(actual.isCellAlive(1, 1)).isTrue();
	}

	@Test
	void testAliveCellWithThreeNeighboursShouldStayAlive() {
		GameOfLife actual = loadFromFile("game_tests/test_alive_with_three_neighbours.txt");

		actual.calculateNextGeneration();
		assertThat(actual.isCellAlive(3, 1)).isTrue();
	}

	@Test
	void testAliveCellWithFourNeighboursShouldDie() {
		GameOfLife actual = loadFromFile("game_tests/test_alive_cell_with_four_neighbours.txt");
		actual.calculateNextGeneration();

		assertThat(actual.isCellAlive(1, 1)).isFalse();
	}

	@Test
	void testDeadCellWithThreeNeighboursShouldBecomeAlive() {
		GameOfLife actual = loadFromFile("game_tests/test_dead_cell_with_three_neighbours.txt");

		actual.calculateNextGeneration();
		assertThat(actual.isCellAlive(7, 3)).isTrue();
	}

	@Test
	void testDeadCellWithFourNeighboursShouldNotBecomeAlive() {
		GameOfLife actual = loadFromFile("game_tests/test_dead_cell_with_four_neighbours.txt");
		actual.calculateNextGeneration();

		assertThat(actual.isCellAlive(1, 1)).isFalse();
	}

	@Test
	void testAliveCellsWithFourNeighboursShouldDie() {
		GameOfLife actual = loadFromFile("game_tests/test_alive_with_four_neighbours.txt");

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
}