package calculator;

import calculator.controller.CalculatorController;
import calculator.domain.Calculator;
import calculator.view.InputView;
import calculator.view.OutputView;

public class Application {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        CalculatorController calculatorController = new CalculatorController(
                calculator, inputView, outputView);

        calculatorController.run();
    }
}
