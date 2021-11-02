package com.github.piotrostrow.gameoflife.io;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

public class FileFormatResolver {

	private FileFormatResolver() { }

	/**
	 * @return File format resolved from the file extension (for the time being)
	 * @throws IllegalArgumentException if the file format is not supported or recognized
	 */
	public static FileFormat resolve(File file) {
		return attemptToResolveByFileExtension(file)
				.orElseThrow(() -> new IllegalArgumentException("File format not supported or recognized"));
	}

	private static Optional<FileFormat> attemptToResolveByFileExtension(File file) {
		return Arrays.stream(FileFormat.values())
				.filter(format -> format.getExtension().equalsIgnoreCase(getFileExtension(file)))
				.findFirst();
	}

	static String getFileExtension(File file) {
		return Optional.of(file.getName())
				.filter(f -> f.contains("."))
				.map(f -> f.substring(f.lastIndexOf(".") + 1))
				.map(String::toLowerCase)
				.orElse("");
	}
}
