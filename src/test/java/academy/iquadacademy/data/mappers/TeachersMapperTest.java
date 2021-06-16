package academy.iquadacademy.data.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.iquadacademy.models.Teachers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class TeachersMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        TeachersMapper teachersMapper = new TeachersMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(anyString())).thenReturn("foo");
        when(resultSet.getInt(anyString())).thenReturn(1);
        int i = 1;

        // Act
        Teachers actualMapRowResult = teachersMapper.mapRow(resultSet, i);

        // Assert
        assertEquals("foo", actualMapRowResult.getFirstName());
        assertEquals(1, actualMapRowResult.getTeachersID());
        assertEquals("foo", actualMapRowResult.getLastName());
        verify(resultSet).getInt(anyString());
        verify(resultSet, times(2)).getString(anyString());
    }
}

