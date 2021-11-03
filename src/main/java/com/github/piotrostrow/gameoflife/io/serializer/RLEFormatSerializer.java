package com.github.piotrostrow.gameoflife.io.serializer;

import com.github.piotrostrow.gameoflife.model.GameOfLife;
import com.github.piotrostrow.gameoflife.model.GridPoint;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RLEFormatSerializer implements Serializer {

	private static final char ALIVE = 'o';
	private static final char DEAD = 'b';
	private static final char END_OF_ROW = '$';
	private static final char END_OF_DATA = '!';

	private final StringBuilder stringBuffer = new StringBuilder();
	private int currentLineLength;

	@Override
	public String serialize(GameOfLife gameOfLife) {
		List<List<GridPoint>> rows = getRows(gameOfLife);

		if (!rows.isEmpty()) {
			serializeRows(rows);
		}

		appendTag(1, END_OF_DATA);

		return stringBuffer.toString();
	}

	private void serializeRows(List<List<GridPoint>> rows) {
		int previousRow = 0;

		for (List<GridPoint> row : rows) {
			int currentRow = row.get(0).getY();

			if (currentRow != previousRow) {
				appendEndOfRow(previousRow, currentRow);
			}

			serializeRow(row);

			previousRow = currentRow;
		}
	}

	private void appendEndOfRow(int previousRow, int currentRow) {
		int runCount = currentRow - previousRow;
		appendTag(runCount, END_OF_ROW);
	}

	private void appendTag(int runCount, char tag) {
		ensureLineLength(runCount);
		if (runCount > 1) {
			stringBuffer.append(runCount);
		}
		stringBuffer.append(tag);
	}

	private void ensureLineLength(int runCount) {
		int length = 1;
		if (runCount > 1) {
			length += Math.ceil(Math.log10(runCount + 1.0));
		}

		if (currentLineLength + length > 70) {
			stringBuffer.append("\n");
			currentLineLength = 0;
		}

		currentLineLength += length;
	}

	private void serializeRow(List<GridPoint> row) {
		int firstAliveCellColumn = row.get(0).getX();
		if (firstAliveCellColumn > 0) {
			appendTag(firstAliveCellColumn, DEAD);
		}

		int firstAliveCellColumnInRun = firstAliveCellColumn;
		int cellsInARow = 0;

		for (GridPoint cell : row) {
			if (cell.x - firstAliveCellColumnInRun <= cellsInARow) {
				cellsInARow++;
			} else {
				appendTag(cellsInARow, ALIVE);
				appendTag(cell.x - cellsInARow - firstAliveCellColumnInRun, DEAD);

				cellsInARow = 1;
				firstAliveCellColumnInRun = cell.x;
			}

		}

		appendTag(cellsInARow, ALIVE);
	}

	private List<List<GridPoint>> getRows(GameOfLife gameOfLife) {
		// offset by min x and y if any cells are on the negative coordinates just to make it easier to work with
		int xOffset = gameOfLife.getAliveCells().stream()
				.map(GridPoint::getX)
				.min(Integer::compare)
				.map(integer -> Math.min(0, integer))
				.orElse(0);
		int yOffset = gameOfLife.getAliveCells().stream()
				.map(GridPoint::getY)
				.min(Integer::compare)
				.map(integer -> Math.min(0, integer))
				.orElse(0);

		List<List<GridPoint>> rows = gameOfLife.getAliveCells().stream()
				.map(point -> new GridPoint(point.x - xOffset, point.y - yOffset))
				.collect(Collectors.groupingBy(GridPoint::getY))
				.entrySet().stream()
				.sorted(Comparator.comparingInt(Map.Entry::getKey))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());

		rows.forEach(row -> row.sort(Comparator.comparingInt(GridPoint::getX)));
		return rows;

	}
}
