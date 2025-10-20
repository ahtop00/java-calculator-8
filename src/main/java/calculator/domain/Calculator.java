package calculator.domain;

import calculator.util.DefaultDelimiterUtil;
import java.util.Arrays;

public class Calculator {
    public int add(String expression) {
        if (expression == null || expression.isBlank()) {
            return 0;
        }
        String[] numberStrings = expression.split(DefaultDelimiterUtil.DEFAULT_DELIMITER_REGEX);
        return sum(numberStrings);
    }

    private int sum(String[] numberStrings) {
        return Arrays.stream(numberStrings)
                .mapToInt(this::toInt)
                .sum();
    }

    private int toInt(String numberString) {
        return Integer.parseInt(numberString);
    }
}
