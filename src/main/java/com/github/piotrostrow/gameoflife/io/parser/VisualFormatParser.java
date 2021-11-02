package com.github.piotrostrow.gameoflife.io.parser;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisualFormatParser implements Parser{

	private static final Pattern PATTERN = Pattern.compile("^\\s*(?<row>[.*]+).*$", Pattern.MULTILINE);

	public static final char ALIVE = '*';
	public static final char DEAD = '.';

	private final String input;
	private final List<String> rows = new ArrayList<>();
	private final GameOfLife gameOfLife = new GameOfLife();

	public VisualFormatParser(String input) {
		this.input = input;
	}

	/**
	 * @throws IllegalArgumentException if the input string has no valid rows
	 */
	@Override
	public GameOfLife parse() {
		if (!rows.isEmpty()) {
			throw new IllegalStateException("Input has already been parsed");
		}

		extractRows();
		parseRows();

		return gameOfLife;
	}

	private void extractRows() {
		Matcher matcher = PATTERN.matcher(input);

		while (matcher.find()) {
			rows.add(matcher.group("row"));
		}

		if (rows.isEmpty()) {
			throw new IllegalArgumentException("Input has no valid rows");
		}
	}

	private void parseRows() {
		for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			String row = rows.get(rowIndex);
			for (int columnIndex = 0; columnIndex < row.length(); columnIndex++) {
				char character = row.charAt(columnIndex);
				gameOfLife.setCell(columnIndex, rowIndex, character == ALIVE);
			}
		}
	}
}
