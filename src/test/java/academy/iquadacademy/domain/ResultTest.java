package academy.iquadacademy.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ResultTest {
    @Test
    public void testIsSuccess() {
        // Arrange
        Result<Object> result = new Result<Object>();

        // Act
        boolean actualIsSuccessResult = result.isSuccess();

        // Assert
        assertTrue(actualIsSuccessResult);
    }

    @Test
    public void testIsSuccess2() {
        // Arrange
        Result<Object> result = new Result<Object>();
        result.addMessage("Not all who wander are lost", ResultType.INVALID);

        // Act
        boolean actualIsSuccessResult = result.isSuccess();

        // Assert
        assertFalse(actualIsSuccessResult);
    }

    @Test
    public void testGetMessages() {
        // Arrange
        Result<Object> result = new Result<Object>();

        // Act
        List<String> actualMessages = result.getMessages();

        // Assert
        assertTrue(actualMessages.isEmpty());
    }

    @Test
    public void testAddMessage() {
        // Arrange
        Result<Object> result = new Result<Object>();
        String message = "Not all who wander are lost";
        ResultType type = ResultType.SUCCESS;

        // Act
        result.addMessage(message, type);

        // Assert
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    public void testConstructor() {
        // Arrange and Act
        Result<Object> actualResult = new Result<Object>();
        String payload = "Payload";
        actualResult.setPayload(payload);

        // Assert
        assertEquals(ResultType.SUCCESS, actualResult.getType());
    }
}

