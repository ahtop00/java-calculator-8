package calculator.domain;

import calculator.util.ConstantUtil;
import calculator.util.CustomDelimiterUtil;
import calculator.util.DefaultDelimiterUtil;
import java.util.regex.Pattern;

public class Parser {
    private static final int CUSTOM_DELIMITER_MARKER_LENGTH = CustomDelimiterUtil.PREFIX.length();

    public static String[] parse(String expression) {
        String[] parts = new String[2];

        if (expression.startsWith(CustomDelimiterUtil.PREFIX) &&
                expression.contains(CustomDelimiterUtil.SUFFIX)) {

            int delimiterEndIndex = expression.indexOf(CustomDelimiterUtil.SUFFIX);
            if (delimiterEndIndex > CUSTOM_DELIMITER_MARKER_LENGTH) {
                String customDelimiter = expression.substring(CUSTOM_DELIMITER_MARKER_LENGTH, delimiterEndIndex);

                int numbersStartIndex = delimiterEndIndex + CustomDelimiterUtil.SUFFIX.length();
                parts[ConstantUtil.NUMBERS_TEXT_INDEX] = expression.substring(numbersStartIndex);
                parts[ConstantUtil.DELIMITERS_INDEX] = buildDelimiterRegex(customDelimiter);
                return parts;
            }
        }

        parts[ConstantUtil.NUMBERS_TEXT_INDEX] = expression;
        parts[ConstantUtil.DELIMITERS_INDEX] = buildDelimiterRegex(null);
        return parts;
    }

    private static String buildDelimiterRegex(String customDelimiter) {
        if (customDelimiter != null) {
            return DefaultDelimiterUtil.DEFAULT_DELIMITER_REGEX + "|" + Pattern.quote(customDelimiter);
        }
        return DefaultDelimiterUtil.DEFAULT_DELIMITER_REGEX;
    }
}
