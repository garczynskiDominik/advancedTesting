package junit.parametrized;


import domain.Calculator;
import junit.parametrized.provider.DataWithResultOfSubstractionProvider;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import validator.WordValidator;

import java.time.Month;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ParametrizedTest {

    private final Calculator calculator = new Calculator();


    @ParameterizedTest
    @ValueSource(ints = {5, 10, 15, 100})
    void shouldReturnTrueAfterDivisibleByFive(int number) {
        Assertions.assertEquals(0, number % 5);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void ShouldReturnTrueForNullOrBlankString(String word) {
        Assertions.assertTrue(WordValidator
                .isBlank(word));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 7, 9})
    void shouldReturnTrueResultAfterAddingValues(int number) {
        //given
        var expectedResult = Stream.of(2d, 4d, 6d, 8d, 10d)
                .collect(Collectors.toList());
        //when
        final var addResult = calculator.add(1, number);
        //then
        assertTrue(expectedResult
                .contains(addResult));
    }

    @ParameterizedTest
    @ValueSource(classes = {String.class, Integer.class, Double.class})
    void shouldPassClassTypeAsParam(Class<?> clazz) {
        //given
        var clazzName = clazz.getName();
        Predicate<String> emptyPredicate = word -> ObjectUtils.isEmpty(word);
        //when
        final var result = emptyPredicate.test(clazzName);

        //then
        assertFalse(result);

    }

    @ParameterizedTest
    @NullSource
    void shouldReturmTrueForNullInputs(String input) {

        //when
        final var result = WordValidator.isBlank(input);
        //then
        assertTrue(result);
    }

    @ParameterizedTest
    @EmptySource
    void shouldReturmTrueForEmptyInputs(String input) {

        //when
        final var result = WordValidator.isBlank(input);
        //then
        assertTrue(result);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void sholudReturnTrueForAllTypesOfBlankString(String input) {
        //when
        final var result = WordValidator.isBlank(input);
        //then
        assertTrue(result);
    }

    @ParameterizedTest
    @EnumSource(Month.class)
    void shouldGetValueForAMonthIsBetween1nad12(Month month) {
        //giveb
        var monthnumber = month.getValue();
        Predicate<Integer> isBeetweenPredicate = number -> number >= 1 && number <= 12;
        //when
        final boolean result = isBeetweenPredicate.test(monthnumber);
        // final boolean result = monthNumber>=1&&monthNumber <=12
        //then
        assertTrue(result);
    }


    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "NOVEMBER"})
    void monthsShouldHave30DaysLong(Month month) {
        //given
        final boolean isALeapYear = false;
        //when
        final var result = month.length(isALeapYear);
        //then
        assertEquals(30, result);

    }

    @ParameterizedTest
    @EnumSource(value = Month.class, names = {"APRIL", "JUNE", "NOVEMBER", "SEPTEMBER", "FEBRUARY"},
            mode = EnumSource.Mode.EXCLUDE)
//            mode = EnumSource.Mode.INCLUDE)
    void shouldExcludeMonthsOthersAre31DaysLong(Month month) {
        //given
        final boolean isALeapYear = false;
        //when
        final var result = month.length(isALeapYear);
        //then
        assertEquals(31, result);

    }

    //CSV
    @ParameterizedTest
    @CsvSource(value = {"course:COURSE", "couRSe:COURSE", "testInG:TESTING", "Testing:TESTING"},
            delimiter = ':')
    void shouldEqualBothValuesAfterUppercase(String input,
                                             String expected) {
        //when
        final var actualValue = input.toUpperCase();
        //then
        assertEquals(expected, actualValue);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/file.csv", numLinesToSkip = 1)
    void shoukFenerateTheExpectedUppercaseValueFromCSVFile(String input, String expected) {
        //when
        final var result = input.toUpperCase();
        //then
        assertEquals(expected, result);
    }

    //METHOD SOURCE
    @ParameterizedTest
    @MethodSource("dataProvider")
    void shouldReturnTrueForNullOrBlankStrings(String input,
                                               boolean expected) {
        //when
        final var result = WordValidator.isBlank(input);
        //then
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource
    void shouldReturnTrueForNullOrBlankStringsOneArgument(String input) {

        //when
        final var result = WordValidator.isBlank(input);
        //then

        assertTrue(result);
    }

    @ParameterizedTest
    @ArgumentsSource(DataWithResultOfSubstractionProvider.class)
    void shouldReturnExpectedValueUsingArgProvider(int data,
                                                   double result) {
        final var resoult = calculator.subtract(data, 1);
        assertEquals(result, resoult);

    }

    private static Stream<String> shouldReturnTrueForNullOrBlankStringsOneArgument() {
        return Stream.of(null, "", " ");
    }

    private static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of("", true),
                Arguments.of("  ", true),
                Arguments.of("not blank", false)

        );
    }


}
