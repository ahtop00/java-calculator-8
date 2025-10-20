package calculator.domain;

import calculator.util.CustomDelimiterUtil;
import calculator.util.DefaultDelimiterUtil;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Calculator {

    private static final int NUMBERS_TEXT_INDEX = 0;
    private static final int DELIMITERS_INDEX = 1;
    private static final int CUSTOM_DELIMITER_MARKER_LENGTH = CustomDelimiterUtil.PREFIX.length();

    public int add(String expression) {
        if (expression == null || expression.isBlank()) {
            return 0;
        }

        String[] parsedParts = parse(expression);
        String numbersText = parsedParts[NUMBERS_TEXT_INDEX];
        String delimiters = parsedParts[DELIMITERS_INDEX];

        if (numbersText.isEmpty()) {
            return 0;
        }

        String[] numberStrings = numbersText.split(delimiters, -1);

        return sum(numberStrings);
    }

    private String[] parse(String expression) {
        String[] parts = new String[2];

        if (expression.startsWith(CustomDelimiterUtil.PREFIX) &&
                expression.contains(CustomDelimiterUtil.SUFFIX)) {

            int delimiterEndIndex = expression.indexOf(CustomDelimiterUtil.SUFFIX);
            if (delimiterEndIndex > CUSTOM_DELIMITER_MARKER_LENGTH) {
                String customDelimiter = expression.substring(CUSTOM_DELIMITER_MARKER_LENGTH, delimiterEndIndex);

                int numbersStartIndex = delimiterEndIndex + CustomDelimiterUtil.SUFFIX.length();
                parts[NUMBERS_TEXT_INDEX] = expression.substring(numbersStartIndex);
                parts[DELIMITERS_INDEX] = buildDelimiterRegex(customDelimiter);
                return parts;
            }
        }

        parts[NUMBERS_TEXT_INDEX] = expression;
        parts[DELIMITERS_INDEX] = buildDelimiterRegex(null);
        return parts;
    }

    private String buildDelimiterRegex(String customDelimiter) {
        if (customDelimiter != null) {
            return DefaultDelimiterUtil.DEFAULT_DELIMITER_REGEX + "|" + Pattern.quote(customDelimiter);
        }
        return DefaultDelimiterUtil.DEFAULT_DELIMITER_REGEX;
    }

    private int sum(String[] numberStrings) {
        return Arrays.stream(numberStrings)
                .mapToInt(this::toInt)
                .sum();
    }

    private int toInt(String numberString) {
        int number;
        try {
            number = Integer.parseInt(numberString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }

        if (number < 0) {
            throw new IllegalArgumentException();
        }
        return number;
    }
}
