package com.github.piotrostrow.gameoflife.io.serializer;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

public interface Serializer {
	String serialize(GameOfLife gameOfLife);
}
