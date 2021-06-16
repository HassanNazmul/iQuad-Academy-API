package academy.iquadacademy.data.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.iquadacademy.models.Student;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class StudentMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        StudentMapper studentMapper = new StudentMapper();
        Date date = mock(Date.class);
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        when(date.toLocalDate()).thenReturn(ofEpochDayResult);
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getDate(anyString())).thenReturn(date);
        when(resultSet.getString(anyString())).thenReturn("foo");
        when(resultSet.getInt(anyString())).thenReturn(1);
        int i = 1;

        // Act
        Student actualMapRowResult = studentMapper.mapRow(resultSet, i);

        // Assert
        assertEquals(1, actualMapRowResult.getDepID());
        assertEquals(1, actualMapRowResult.getStudentID());
        assertEquals("foo", actualMapRowResult.getLastName());
        assertEquals("foo", actualMapRowResult.getFirstName());
        assertSame(ofEpochDayResult, actualMapRowResult.getDob());
        verify(resultSet, times(2)).getDate(anyString());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet, times(2)).getString(anyString());
        verify(date).toLocalDate();
    }
}

