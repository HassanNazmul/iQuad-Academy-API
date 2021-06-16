package academy.iquadacademy.data.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.iquadacademy.models.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class DepartmentMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        DepartmentMapper departmentMapper = new DepartmentMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(anyString())).thenReturn("foo");
        when(resultSet.getInt(anyString())).thenReturn(1);
        int i = 1;

        // Act
        Department actualMapRowResult = departmentMapper.mapRow(resultSet, i);

        // Assert
        assertEquals(1, actualMapRowResult.getDepCode());
        assertEquals("foo", actualMapRowResult.getDepName());
        assertEquals(1, actualMapRowResult.getDepID());
        verify(resultSet, times(2)).getInt(anyString());
        verify(resultSet).getString(anyString());
    }
}

