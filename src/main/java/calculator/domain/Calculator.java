package calculator.domain;

import calculator.util.ConstantUtil;
import java.util.Arrays;

public class Calculator {
    public int add(String expression) {
        if (expression == null || expression.isBlank()) {
            return 0;
        }

        String[] parsedParts = Parser.parse(expression);
        String numbersText = parsedParts[ConstantUtil.NUMBERS_TEXT_INDEX];
        String delimiters = parsedParts[ConstantUtil.DELIMITERS_INDEX];

        if (numbersText.isEmpty()) {
            return 0;
        }

        String[] numberStrings = numbersText.split(delimiters, -1);

        return sum(numberStrings);
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
