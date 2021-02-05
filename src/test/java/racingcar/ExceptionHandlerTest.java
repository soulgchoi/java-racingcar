package racingcar;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ExceptionHandlerTest {

    @Test
    @DisplayName("입력한 이름대로 올바르게 Car 리스트가 만들어지는지 확인")
    void setCarsTest() {
        List<Car> expected = new ArrayList<>();
        expected.add(new Car("루트"));
        expected.add(new Car("소롱"));

        List<Car> actual = ExceptionHandler.setCars("루트,소롱");

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"한대의자동차", "자동차#;"})
    void setCarsTest_자동차_입력_수(String input) {
        assertThatThrownBy(() -> {
            ExceptionHandler.setCars(input);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("자동차는 두 대 이상 입력해야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"루트,소롱,루트", "루트,루트,루트"})
    void setCarsTest_중복되는_자동차_입력(String input) {
        assertThatThrownBy(() -> {
            ExceptionHandler.setCars(input);
        }).isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("중복되는 이름을 입력할 수 없습니다.");
    }


    @Test
    @DisplayName("입력한 시도 회수가 정상적인 값인지 확인")
    void setTrialTest() {
        int expected = 3;
        int actual = ExceptionHandler.setTrial("3");
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1", "2,2", "50,50","2147483647,2147483647"}, delimiter = ',')
    void setTrial_정상입력(String input, Integer expected) {
        Integer actual = ExceptionHandler.setTrial(input);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2147483648", "", "2-1", "abc", "894-"})
    void setTrial_비정상입력(String input) {
        assertThatThrownBy(() -> {
            ExceptionHandler.setTrial(input);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
