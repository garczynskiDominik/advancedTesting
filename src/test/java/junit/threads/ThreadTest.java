package junit.threads;

import domain.Calculator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThreadTest {

    private final Calculator calculator = new Calculator();
    private final List<BigDecimal> bigDecimals = List.of(BigDecimal.ONE, BigDecimal.ZERO);


    //JUNIT
    @Test
    void shouldThrowNumberFormatException() {
        //given
        var word = "One";
        //when
        final var result = assertThrows(NumberFormatException.class, () -> Integer.parseInt(word));
        //then
        assertEquals(NumberFormatException.class, result.getClass());
        assertEquals("For input string: \"One\"", result.getMessage());


    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionExampleOne() {

        //given
        var listOfNumber = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        //when
        final var result = assertThrows(IndexOutOfBoundsException.class, () -> listOfNumber.get(10));

        //then
        assertEquals("Index 10 out of bounds for lenght 10", result.getMessage());

    }

    //ASSERTJ

    @Test
    void shouldThrowArithmeticExceptionExampleOne() {
//when
        final var exception = assertThrows(ArithmeticException.class, () -> calculator.divide(10, 0));
        //then
        assertThat(exception)
                .hasMessage("/ by zero")
                .hasNoCause();
    }

    @Test
    void shouldThrowArithmeticExceptionExampleTwo() {
        assertThatThrownBy(() -> calculator.divide(1, 0))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("zero")
                .hasMessage("/ by zero");
    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionExampleTwo() {
        assertThatThrownBy(() -> bigDecimals.get(2))
                .isInstanceOf(IndexOutOfBoundsException.class)
                .hasMessageContaining("Index: 2 Size: 2");

    }

    @Test
    void shouldThrowIndexOutOfBoundsExceptionExampleThree() {
        assertThatExceptionOfType(IndexOutOfBoundsException.class)
                .isThrownBy(() -> bigDecimals.get(2))
                .withMessage("Index:2 Size: 2")
                .withNoCause();
    }


}
