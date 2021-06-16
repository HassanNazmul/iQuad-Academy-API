package academy.iquadacademy.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ResultTypeTest {
    @Test
    public void testValueOf() {
        // Arrange
        String name = "INVALID";

        // Act
        ResultType actualValueOfResult = ResultType.valueOf(name);

        // Assert
        assertEquals(ResultType.INVALID, actualValueOfResult);
    }

    @Test
    public void testValues() {
        // Arrange and Act
        ResultType[] actualValuesResult = ResultType.values();

        // Assert
        assertEquals(3, actualValuesResult.length);
        assertEquals(ResultType.SUCCESS, actualValuesResult[0]);
        assertEquals(ResultType.INVALID, actualValuesResult[1]);
        assertEquals(ResultType.NOT_FOUND, actualValuesResult[2]);
    }
}

