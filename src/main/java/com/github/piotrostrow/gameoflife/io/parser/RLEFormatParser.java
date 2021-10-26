package com.github.piotrostrow.gameoflife.io.parser;

import com.github.piotrostrow.gameoflife.game.GameOfLife;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RLEFormatParser implements Parser{

	private static final Pattern LINE_PATTERN = Pattern.compile("^(?<input>[\\dbo$!](((\\d+)?[bo]?)+\\$?)+!?).*", Pattern.MULTILINE);
	private static final Pattern DISCARD_AFTER_EXCLAMATION_MARK = Pattern.compile("(?<validInput>[^!]*!).*");
	private static final Pattern ROW_PATTERN = Pattern.compile("(?<row>((\\d+)?[bo]?)+[$!])");
	private static final Pattern COUNT_TAG_PATTERN = Pattern.compile("(?<count>\\d+)?(?<tag>[bo])");
	private static final Pattern ROW_END_MATCHER = Pattern.compile("(?<count>\\d+)?\\$");

	private static final String ALIVE = "o";

	private final String input;
	private final GameOfLife gameOfLife = new GameOfLife();

	private int currentRow;
	private int currentColumn;

	public RLEFormatParser(String input) {
		this.input = input;
	}

	@Override
	public GameOfLife parse() {
		String rowsAsSingleLineString = extractRowsAsSingleLineString();
		Matcher rowMatcher = ROW_PATTERN.matcher(rowsAsSingleLineString);

		while(rowMatcher.find()) {
			String row = rowMatcher.group("row");
			parseRow(row);
		}

		return gameOfLife;
	}

	private String extractRowsAsSingleLineString() {
		return input.lines()
				.map(LINE_PATTERN::matcher)
				.filter(Matcher::find)
				.map(matcher -> matcher.group("input"))
				.reduce(String::concat)
				.map(DISCARD_AFTER_EXCLAMATION_MARK::matcher)
				.filter(Matcher::find)
				.map(matcher -> matcher.group("validInput"))
				.orElseThrow(() -> new IllegalArgumentException("Invalid input string"));
	}

	private void parseRow(String row) {
		Matcher runCountTagMatcher = COUNT_TAG_PATTERN.matcher(row);
		while(runCountTagMatcher.find()) {
			String tag = runCountTagMatcher.group("tag");
			String countString = runCountTagMatcher.group("count");
			parseTag(tag, countString);
		}

		parseEndOfRow(row);
	}

	private void parseTag(String tag, String countString) {
		int count = parseIntOrOne(countString);
		if (tag.equals(ALIVE)){
			for(int i = 0; i < count; i++)  {
				gameOfLife.setCell(currentColumn + i, currentRow, true);
			}
		}
		currentColumn += count;
	}

	private void parseEndOfRow(String row) {
		Matcher rowEndMatcher = ROW_END_MATCHER.matcher(row);
		if (rowEndMatcher.find()) {
			String countString = rowEndMatcher.group("count");
			currentRow += parseIntOrOne(countString);
		}
		currentColumn = 0;
	}

	private static int parseIntOrOne(String input) {
		// No need to check if it's a valid integer cus the value matched the pattern, can only possibly be null otherwise
		if(input == null) {
			return 1;
		}
		return Integer.parseInt(input);
	}
}
