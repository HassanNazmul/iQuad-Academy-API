package academy.iquadacademy.data.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.iquadacademy.models.Module;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class ModuleMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        // Arrange
        ModuleMapper moduleMapper = new ModuleMapper();
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(anyString())).thenReturn("foo");
        when(resultSet.getInt(anyString())).thenReturn(1);
        int i = 1;

        // Act
        Module actualMapRowResult = moduleMapper.mapRow(resultSet, i);

        // Assert
        assertEquals(1, actualMapRowResult.getDepID());
        assertEquals("foo", actualMapRowResult.getModName());
        assertEquals(1, actualMapRowResult.getModID());
        assertEquals(1, actualMapRowResult.getModCode());
        verify(resultSet, times(3)).getInt(anyString());
        verify(resultSet).getString(anyString());
    }
}

