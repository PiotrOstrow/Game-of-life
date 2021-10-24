package com.github.piotrostrow.gameoflife.io.serializer;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class RLEFormatSerializerTest {

	Serializer serializer = new RLEFormatSerializer();

	@Test
	void testEmptyGridShouldProduceSingleDeadCellWithProperTermination() {
		GameOfLife gameOfLife = new GameOfLife();

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("!");
	}

	@Test
	void testSingleLiveCellShouldProduceSingleLiveCellWithProperTermination() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(0, 0, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("o!");
	}

	@Test
	void testSingleLiveCellNotAtOriginShouldPadWithDeadCellsAndEmptyRows() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(2, 2, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("2$2bo!");
	}

	@Test
	void testDeadAndAliveCellsInterleaved() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(0, 0, true);
		gameOfLife.setCell(2, 0, true);
		gameOfLife.setCell(4, 0, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("obobo!");
	}

	@Test
	void testWithOneCellAtNegativeCoordinates() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(-2, -2, true);
		gameOfLife.setCell(0, 0, true);
		gameOfLife.setCell(2, 2, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("o2$2bo2$4bo!");
	}

	@Test
	void testLiveCellsOnDifferentRowsShouldProduceMultipleRowsWithRowBreaksAndProperTermination() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(0, 0, true);
		gameOfLife.setCell(0, 1, true);
		gameOfLife.setCell(0, 2, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("o$o$o!");
	}

	@Test
	void testLiveCellsOnDifferentRowsWithManyRowsInBetweenShouldProduceRunCountForRowBreakCharacter() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(0, 0, true);
		gameOfLife.setCell(0, 2, true);
		gameOfLife.setCell(0, 7, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("o2$o5$o!");
	}

	@Test
	void testSeveralLiveCellsInLineWithSeveralDeadCellsInALineShouldProduceOutputWithRunCountAndProperTermination() {
		GameOfLife gameOfLife = new GameOfLife();
		gameOfLife.setCell(0, 0, true);
		gameOfLife.setCell(1, 0, true);
		gameOfLife.setCell(20, 0, true);
		gameOfLife.setCell(21, 0, true);
		gameOfLife.setCell(22, 0, true);
		gameOfLife.setCell(24, 0, true);

		String actual = serializer.serialize(gameOfLife);

		assertThat(actual.trim()).isEqualTo("2o18b3obo!");
	}

	@Test
	void testLargelyPopulatedGridLineLengthShouldNotExceed70() {
		GameOfLife gameOfLife = new GameOfLife();
		for(int i = 0; i < 500; i++) {
			int x = (int) (Math.random() * 100);
			int y = (int) (Math.random() * 100);
			gameOfLife.setCell(x, y, true);
		}

		String actual = serializer.serialize(gameOfLife);

		actual.lines().forEach(line -> assertThat(line.length()).isLessThanOrEqualTo(70));
	}

	@Test
	void testLineBreakShouldNotBreakOnAnInteger() {
		GameOfLife gameOfLife = new GameOfLife();
		for(int i = 0; i < 100; i++) {
			gameOfLife.setCell(0, i, true);
			gameOfLife.setCell(1 << 30, i, true);
		}

		String actual = serializer.serialize(gameOfLife);

		Pattern pattern = Pattern.compile("\\d(\\r\\n|\\r|\\n)\\d");
		Matcher matcher = pattern.matcher(actual);
		assertThat(matcher.find()).isFalse();
	}
}