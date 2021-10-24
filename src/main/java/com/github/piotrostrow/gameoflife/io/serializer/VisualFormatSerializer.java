package com.github.piotrostrow.gameoflife.io.serializer;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.game.GridPoint;
import com.github.piotrostrow.gameoflife.io.parser.VisualFormatParser;

import java.util.Comparator;

public class VisualFormatSerializer implements Serializer{


	@Override
	public String serialize(GameOfLife gameOfLife) {
		StringBuilder stringBuilder = new StringBuilder();

		int minX = findMinX(gameOfLife);
		int maxX = findMaxX(gameOfLife);
		int minY = findMinY(gameOfLife);
		int maxY = findMaxY(gameOfLife);

		for(int y = minY; y <= maxY; y++) {
			for(int x = minX; x <= maxX; x++) {
				char character = gameOfLife.isCellAlive(x, y) ? VisualFormatParser.ALIVE : VisualFormatParser.DEAD;
				stringBuilder.append(character);
			}
			stringBuilder.append('\n');
		}

		return stringBuilder.toString();
	}

	private static int findMinX(GameOfLife gameOfLife) {
		return gameOfLife.getAliveCells().stream()
				.map(GridPoint::getX)
				.min(Comparator.naturalOrder())
				.orElse(0);
	}

	private static int findMaxX(GameOfLife gameOfLife) {
		return gameOfLife.getAliveCells().stream()
				.map(GridPoint::getX)
				.max(Comparator.naturalOrder())
				.orElse(0);
	}

	private static int findMinY(GameOfLife gameOfLife) {
		return gameOfLife.getAliveCells().stream()
				.map(GridPoint::getY)
				.min(Comparator.naturalOrder())
				.orElse(0);
	}

	private static int findMaxY(GameOfLife gameOfLife) {
		return gameOfLife.getAliveCells().stream()
				.map(GridPoint::getY)
				.max(Comparator.naturalOrder())
				.orElse(0);
	}
}
