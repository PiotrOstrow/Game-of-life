package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

import java.io.*;
import java.nio.file.Files;

public class FileLoader {

	public static GameOfLife load(File file) {
		try {
			String input = Files.readString(file.toPath());
			Parser parser = new Parser(input);
			return parser.parse();
		} catch (IOException e) {
			e.printStackTrace();
			return new GameOfLife(15, 15);
		}
	}
}
