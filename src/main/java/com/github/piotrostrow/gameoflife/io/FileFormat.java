package com.github.piotrostrow.gameoflife.io;

public enum FileFormat {
	VISUAL_FORMAT("Visual format text file", "txt"),
	RLE_FORMAT("Run Length Encoded file", "rle");

	private final String description;
	private final String extension;

	FileFormat(String description, String extension) {
		this.description = description;
		this.extension = extension;
	}

	public String getDescription() {
		return description;
	}

	public String getExtension() {
		return extension;
	}
}
