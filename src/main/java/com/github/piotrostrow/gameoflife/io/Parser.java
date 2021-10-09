package com.github.piotrostrow.gameoflife.io;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private static final Pattern pattern = Pattern.compile("^\\s*(?<row>[.*]+).*$", Pattern.MULTILINE);

	private static final char ALIVE = '*';
	private static final char DEAD = '.';

	private final String input;
	private final List<String> rows = new ArrayList<>();
	private GameOfLife gameOfLife;

	public Parser(String input) {
		this.input = input;
	}

	public GameOfLife parse() {
		extractRows();
		constructGame();
		parseRows();

		return gameOfLife;
	}

	private void extractRows() {
		Matcher matcher = pattern.matcher(input);

		while(matcher.find()) {
			rows.add(matcher.group("row"));
		}
	}

	private void constructGame() {
		int height = rows.size();
		int width = rows.stream()
				.map(String::length)
				.max(Comparator.naturalOrder())
				.orElse(1);

		gameOfLife = new GameOfLife(width, height);
	}

	private void parseRows() {
		for(int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
			String row = rows.get(rowIndex);
			for(int columnIndex = 0; columnIndex < row.length(); columnIndex++) {
				char character = row.charAt(columnIndex);
				gameOfLife.setCell(columnIndex, rowIndex, character == ALIVE);
			}
		}
	}
}
