package calculator.controller;

import calculator.domain.Calculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {
    private final Calculator calculator;
    private final InputView inputView;
    private final OutputView outputView;

    public CalculatorController(Calculator calculator, InputView inputView, OutputView outputView) {
        this.calculator = calculator;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        try {
            outputView.printInputPrompt();

            String expression = inputView.readExpression();

            outputView.printResult(calculator.add(expression));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}
