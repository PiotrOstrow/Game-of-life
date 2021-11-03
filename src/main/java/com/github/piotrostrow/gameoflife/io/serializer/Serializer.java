package com.github.piotrostrow.gameoflife.io.serializer;

import com.github.piotrostrow.gameoflife.model.GameOfLife;

public interface Serializer {
	String serialize(GameOfLife gameOfLife);
}
