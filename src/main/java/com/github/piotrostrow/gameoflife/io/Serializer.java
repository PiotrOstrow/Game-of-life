package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.game.GridPoint;

import java.util.Comparator;

public class Serializer {

	private static final int DEFAULT_MAX = 4;

	public static String serialize(GameOfLife gameOfLife) {
		StringBuilder stringBuilder = new StringBuilder();

		int minX = findMinX(gameOfLife);
		int maxX = findMaxX(gameOfLife);
		int minY = findMinY(gameOfLife);
		int maxY = findMaxY(gameOfLife);

		for(int y = minY; y <= maxY; y++) {
			for(int x = minX; x <= maxX; x++) {
				char character = gameOfLife.isCellAlive(x, y) ? Parser.ALIVE : Parser.DEAD;
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
				.orElse(DEFAULT_MAX - 1);
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
				.orElse(DEFAULT_MAX - 1);
	}
}
