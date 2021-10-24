package com.github.piotrostrow.gameoflife.io.serializer;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class VisualFormatSerializerTest {

	Serializer serializer = new VisualFormatSerializer();

	@Test
	void testEmptyGridShouldProduceSingleDeadCell() {
		GameOfLife gameOfLife = new GameOfLife();

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo(".");
	}

	@Test
	void testSingleLiveCellAtOriginShouldProduceSingleLiveCell() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(0, 0, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("*");
	}

	@Test
	void testSingleLiveCellAtArbitraryPointShouldProduceSingleLiveCell() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(10, 6, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("*");
	}

	@Test
	void testTwoLiveCellsAtOriginAndPositiveCoordinatesShouldProduceMatrix() {
		GameOfLife gameOfLife = new GameOfLife();

		gameOfLife.setCell(0, 0, true);
		gameOfLife.setCell(2, 2, true);

		List<String> actual = toLineList(serializer.serialize(gameOfLife));
		List<String> expected = toLineList("*..\n...\n..*");

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void testTwoLiveCellsAtOriginAndNegativeCoordinatesShouldProduceMatrix() {
		GameOfLife gameOfLife = new GameOfLife();

		gameOfLife.setCell(0, 0, true);
		gameOfLife.setCell(-2, -2, true);
		gameOfLife.setCell(-1, -2, true);
		gameOfLife.setCell(-2, -1, true);
		gameOfLife.setCell(-1, -1, true);
		gameOfLife.setCell(0, -1, true);

		List<String> actual = toLineList(serializer.serialize(gameOfLife));
		List<String> expected = toLineList("**.\n***\n..*");

		assertThat(actual).isEqualTo(expected);
	}

	// accounts for different line break characters
	private List<String> toLineList(String string) {
		return string.lines().collect(Collectors.toList());
	}
}