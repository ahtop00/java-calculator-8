package calculator.domain;

public class Calculator {
    public int add(String expression) {
        if (expression == null || expression.isBlank()) {
            return 0;
        }
        return Integer.parseInt(expression);
    }
}
