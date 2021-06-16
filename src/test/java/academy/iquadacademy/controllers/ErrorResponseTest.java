package academy.iquadacademy.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import academy.iquadacademy.domain.Result;
import academy.iquadacademy.domain.ResultType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponseTest {
    @Test
    public void testConstructor() {
        // Arrange
        String message = "Not all who wander are lost";

        // Act
        ErrorResponse actualErrorResponse = new ErrorResponse(message);

        // Assert
        assertEquals("Not all who wander are lost", actualErrorResponse.getMessage());
    }

    @Test
    public void testBuild() {
        // Arrange
        Result<Object> result = new Result<Object>();

        // Act
        ResponseEntity<Object> actualBuildResult = ErrorResponse.<Object>build(result);

        // Assert
        assertEquals("<500 INTERNAL_SERVER_ERROR Internal Server Error,[],[]>", actualBuildResult.toString());
        assertTrue(actualBuildResult.hasBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualBuildResult.getStatusCode());
        assertTrue(actualBuildResult.getHeaders().isEmpty());
    }

    @Test
    public void testBuild2() {
        // Arrange
        Result<Object> result = new Result<Object>();
        result.addMessage("Not all who wander are lost", ResultType.SUCCESS);

        // Act
        ResponseEntity<Object> actualBuildResult = ErrorResponse.<Object>build(result);

        // Assert
        assertEquals("<500 INTERNAL_SERVER_ERROR Internal Server Error,[Not all who wander are lost],[]>",
                actualBuildResult.toString());
        assertTrue(actualBuildResult.hasBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualBuildResult.getStatusCode());
        assertTrue(actualBuildResult.getHeaders().isEmpty());
    }

    @Test
    public void testBuild3() {
        // Arrange
        Result<Object> result = new Result<Object>();
        result.addMessage("Not all who wander are lost", null);

        // Act
        ResponseEntity<Object> actualBuildResult = ErrorResponse.<Object>build(result);

        // Assert
        assertEquals("<400 BAD_REQUEST Bad Request,[Not all who wander are lost],[]>", actualBuildResult.toString());
        assertTrue(actualBuildResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualBuildResult.getStatusCode());
        assertTrue(actualBuildResult.getHeaders().isEmpty());
    }

    @Test
    public void testBuild4() {
        // Arrange
        Result<Object> result = new Result<Object>();
        result.addMessage("Not all who wander are lost", ResultType.INVALID);

        // Act
        ResponseEntity<Object> actualBuildResult = ErrorResponse.<Object>build(result);

        // Assert
        assertEquals("<400 BAD_REQUEST Bad Request,[Not all who wander are lost],[]>", actualBuildResult.toString());
        assertTrue(actualBuildResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualBuildResult.getStatusCode());
        assertTrue(actualBuildResult.getHeaders().isEmpty());
    }

    @Test
    public void testBuild5() {
        // Arrange
        Result<Object> result = new Result<Object>();
        result.addMessage("Not all who wander are lost", ResultType.NOT_FOUND);

        // Act
        ResponseEntity<Object> actualBuildResult = ErrorResponse.<Object>build(result);

        // Assert
        assertEquals("<404 NOT_FOUND Not Found,[Not all who wander are lost],[]>", actualBuildResult.toString());
        assertTrue(actualBuildResult.hasBody());
        assertEquals(HttpStatus.NOT_FOUND, actualBuildResult.getStatusCode());
        assertTrue(actualBuildResult.getHeaders().isEmpty());
    }
}

