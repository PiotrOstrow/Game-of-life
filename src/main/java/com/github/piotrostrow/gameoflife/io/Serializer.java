package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

public class Serializer {

	public static String serialize(GameOfLife gameOfLife) {
		StringBuilder stringBuilder = new StringBuilder();

		for(int y = 0; y < gameOfLife.getHeight(); y++) {
			for(int x = 0; x < gameOfLife.getWidth(); x++) {
				char character = gameOfLife.isCellAlive(x, y) ? Parser.ALIVE : Parser.DEAD;
				stringBuilder.append(character);
			}
			stringBuilder.append('\n');
		}

		return stringBuilder.toString();
	}
}
