package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;
import com.github.piotrostrow.gameoflife.io.parser.Parser;
import com.github.piotrostrow.gameoflife.io.parser.RLEFormatParser;
import com.github.piotrostrow.gameoflife.io.parser.VisualFormatParser;
import com.github.piotrostrow.gameoflife.io.serializer.RLEFormatSerializer;
import com.github.piotrostrow.gameoflife.io.serializer.Serializer;
import com.github.piotrostrow.gameoflife.io.serializer.VisualFormatSerializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileUtils {

	private FileUtils() { }

	public static GameOfLife load(String path) {
		return load(new File(path));
	}

	public static GameOfLife load(File file) {
		try {
			String input = Files.readString(file.toPath());
			Parser parser = getParser(file, input);
			return parser.parse();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static Parser getParser(File file, String fileContents) {
		switch (FileFormatResolver.resolve(file)) {
			case VISUAL_FORMAT: return new VisualFormatParser(fileContents);
			case RLE_FORMAT: 	return new RLEFormatParser(fileContents);
			default:			throw new IllegalStateException();
		}
	}

	public static void save(GameOfLife gameOfLife, File file) {
		try {
			Serializer serializer = getSerializer(file);
			String serialized = serializer.serialize(gameOfLife);
			Files.writeString(file.toPath(), serialized, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private static Serializer getSerializer(File file) {
		switch (FileFormatResolver.resolve(file)) {
			case VISUAL_FORMAT: return new VisualFormatSerializer();
			case RLE_FORMAT:	return new RLEFormatSerializer();
			default:			throw new IllegalStateException();
		}
	}
}
