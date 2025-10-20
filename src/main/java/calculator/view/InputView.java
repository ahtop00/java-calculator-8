package calculator.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public String open() {
        return Console.readLine();
    }

    public void close() {
        Console.close();
    }
}
