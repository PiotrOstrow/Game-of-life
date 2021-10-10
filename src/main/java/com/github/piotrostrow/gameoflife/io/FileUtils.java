package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileUtils {

	public static GameOfLife load(File file) {
		try {
			String input = Files.readString(file.toPath());
			Parser parser = new Parser(input);
			return parser.parse();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void save(GameOfLife gameOfLife, File file) {
		try {
			String serialized = Serializer.serialize(gameOfLife);
			Files.writeString(file.toPath(), serialized, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
