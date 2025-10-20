package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    @DisplayName("정상: 빈 문자열 입력 시 0을 반환한다")
    void empty_string_returns_zero() {
        assertSimpleTest(() -> {
            run("\n");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    @DisplayName("정상: 숫자 하나 입력 시 해당 숫자를 반환한다")
    void single_number_returns_itself() {
        assertSimpleTest(() -> {
            run("1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    @DisplayName("정상: 기본 구분자(쉼표, 콜론)로 덧셈을 수행한다")
    void default_delimiters_sum() {
        assertSimpleTest(() -> {
            run("1:2,3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    @DisplayName("정상: 커스텀 구분자로 덧셈을 수행한다")
    void custom_delimiter_sum() {
        assertSimpleTest(() -> {
            // 주의: 테스트 코드에서는 실제 줄바꿈을 위해 \\n가 아닌 \n을 사용합니다.
            run("//+\\n1+23+4");
            assertThat(output()).contains("결과 : 28");
        });
    }

    @Test
    @DisplayName("정상: 기본 구분자와 커스텀 구분자를 혼용하여 덧셈을 수행한다")
    void mixed_delimiters_sum() {
        assertSimpleTest(() -> {
            run("//^\\n1^2:34,10");
            assertThat(output()).contains("결과 : 47");
        });
    }

    @Test
    @DisplayName("엣지: 커스텀 구분자가 숫자일 경우 덧셈을 수행한다")
    void numeric_custom_delimiter() {
        assertSimpleTest(() -> {
            run("//0\\n2010304");
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    @DisplayName("엣지: 커스텀 구분자가 마침표(.)일 경우 덧셈을 수행한다")
    void dot_custom_delimiter() {
        assertSimpleTest(() -> {
            run("//.\\n2.3.4.5.10");
            assertThat(output()).contains("결과 : 24");
        });
    }

    @Test
    @DisplayName("엣지: 커스텀 구분자가 하이픈(-)일 경우 덧셈을 수행한다")
    void hyphen_custom_delimiter() {
        assertSimpleTest(() -> {
            run("//-\\n2-3-4-5");
            assertThat(output()).contains("결과 : 14");
        });
    }

    @Test
    @DisplayName("예외: 음수 값이 포함된 경우 IllegalArgumentException을 발생시킨다")
    void exception_on_negative_number() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("1,-2,3"))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    @DisplayName("예외: 숫자가 아닌 문자가 포함된 경우 IllegalArgumentException을 발생시킨다")
    void exception_on_non_numeric_character() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("1,b,2,3"))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    @DisplayName("예외: 커스텀 구분자 형식이 올바르지 않은 경우 IllegalArgumentException을 발생시킨다")
    void exception_on_invalid_custom_delimiter_format() {
        assertSimpleTest(() -> {
            // 이 입력은 커스텀 구분자로 인식되지 않고, "//k2k3"을 숫자로 변환하려다 실패해야 합니다.
            assertThatThrownBy(() -> runException("//k2k3,4"))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    @DisplayName("예외: 문자열이 구분자로 끝나는 경우 IllegalArgumentException을 발생시킨다")
    void exception_on_ending_with_delimiter() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("1,3:"))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
