package com.github.piotrostrow.gameoflife.model;

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
		GameOfLife actual = loadFromFile("game_tests/test_multiple_alive_cells_with_no_neigbours.txt");

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

		assertThat(actual.getAliveCells().size()).isOne();
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
	void testThreeAliveCellsAlongXAxisEdgeShouldTurnDeadCellAtNegativeYAlive() {
		GameOfLife actual = loadFromFile("game_tests/test_along_axis_x.txt");

		actual.calculateNextGeneration();

		assertThat(actual.getAliveCells().size()).isEqualTo(3);
		assertThat(actual.isCellAlive(2, 1)).isTrue();
		assertThat(actual.isCellAlive(2, 0)).isTrue();
		assertThat(actual.isCellAlive(2, -1)).isTrue();
	}

	@Test
	void testThreeAliveCellsAlongYAxisEdgeShouldTurnDeadCellAtNegativeXAlive() {
		GameOfLife actual = loadFromFile("game_tests/test_along_axis_y.txt");

		actual.calculateNextGeneration();

		assertThat(actual.getAliveCells().size()).isEqualTo(3);
		assertThat(actual.isCellAlive(1, 2)).isTrue();
		assertThat(actual.isCellAlive(0, 2)).isTrue();
		assertThat(actual.isCellAlive(-1, 2)).isTrue();
	}

	@Test
	void testThreeCellsByPositiveXEdgeShouldNotSpawnCellOutOfBounds() {
		GameOfLife actual = new GameOfLife();

		actual.setCell(Integer.MAX_VALUE, 0, true);
		actual.setCell(Integer.MAX_VALUE, 1, true);
		actual.setCell(Integer.MAX_VALUE, 2, true);

		actual.calculateNextGeneration();

		assertThat(actual.getAliveCells().size()).isEqualTo(2);
		assertThat(actual.isCellAlive(Integer.MAX_VALUE, 1)).isTrue();
		assertThat(actual.isCellAlive(Integer.MAX_VALUE - 1, 1)).isTrue();
	}

	@Test
	void testThreeCellsByNegativeXEdgeShouldNotSpawnCellOutOfBounds() {
		GameOfLife actual = new GameOfLife();

		actual.setCell(Integer.MIN_VALUE, 0, true);
		actual.setCell(Integer.MIN_VALUE, 1, true);
		actual.setCell(Integer.MIN_VALUE, 2, true);

		actual.calculateNextGeneration();

		assertThat(actual.getAliveCells().size()).isEqualTo(2);
		assertThat(actual.isCellAlive(Integer.MIN_VALUE, 1)).isTrue();
		assertThat(actual.isCellAlive(Integer.MIN_VALUE + 1, 1)).isTrue();
	}

	@Test
	void testThreeCellsByPositiveYEdgeShouldNotSpawnCellsOutOfBounds() {
		GameOfLife actual = new GameOfLife();

		actual.setCell(0, Integer.MAX_VALUE, true);
		actual.setCell(1, Integer.MAX_VALUE, true);
		actual.setCell(2, Integer.MAX_VALUE, true);

		actual.calculateNextGeneration();

		assertThat(actual.getAliveCells().size()).isEqualTo(2);
		assertThat(actual.isCellAlive(1, Integer.MAX_VALUE)).isTrue();
		assertThat(actual.isCellAlive(1, Integer.MAX_VALUE - 1)).isTrue();
	}

	@Test
	void testThreeCellsByNegativeYEdgeShouldNotSpawnCellOutOfBounds() {
		GameOfLife actual = new GameOfLife();

		actual.setCell(0, Integer.MIN_VALUE, true);
		actual.setCell(1, Integer.MIN_VALUE, true);
		actual.setCell(2, Integer.MIN_VALUE, true);

		actual.calculateNextGeneration();

		assertThat(actual.getAliveCells().size()).isEqualTo(2);
		assertThat(actual.isCellAlive(1, Integer.MIN_VALUE)).isTrue();
		assertThat(actual.isCellAlive(1, Integer.MIN_VALUE + 1)).isTrue();
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