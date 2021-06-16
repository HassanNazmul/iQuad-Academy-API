package academy.iquadacademy.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.iquadacademy.data.TeachersRepository;
import academy.iquadacademy.models.Teachers;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TeachersService.class})
@ExtendWith(SpringExtension.class)
public class TeachersServiceTest {
    @MockBean
    private TeachersRepository teachersRepository;

    @Autowired
    private TeachersService teachersService;

    @Test
    public void testFindAll() {
        // Arrange
        ArrayList<Teachers> teachersList = new ArrayList<Teachers>();
        when(this.teachersRepository.findAll()).thenReturn(teachersList);

        // Act
        List<Teachers> actualFindAllResult = this.teachersService.findAll();

        // Assert
        assertSame(teachersList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.teachersRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Arrange
        Teachers teachers = new Teachers();
        when(this.teachersRepository.findById(anyInt())).thenReturn(teachers);
        int teachersID = 1;

        // Act
        Teachers actualFindByIdResult = this.teachersService.findById(teachersID);

        // Assert
        assertSame(teachers, actualFindByIdResult);
        verify(this.teachersRepository).findById(anyInt());
        assertTrue(this.teachersService.findAll().isEmpty());
    }

    @Test
    public void testAdd() {
        // Arrange
        Teachers teachers = new Teachers();
        when(this.teachersRepository.add((Teachers) any())).thenReturn(teachers);
        Teachers teachers1 = new Teachers();

        // Act
        Result<Teachers> actualAddResult = this.teachersService.add(teachers1);

        // Assert
        assertTrue(actualAddResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualAddResult.getType());
        assertSame(teachers, actualAddResult.getPayload());
        verify(this.teachersRepository).add((Teachers) any());
        assertTrue(this.teachersService.findAll().isEmpty());
    }

    @Test
    public void testAdd2() {
        // Arrange
        when(this.teachersRepository.add((Teachers) any())).thenReturn(new Teachers());
        Teachers teachers = null;

        // Act
        Result<Teachers> actualAddResult = this.teachersService.add(teachers);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
    }

    @Test
    public void testAdd3() {
        // Arrange
        when(this.teachersRepository.add((Teachers) any())).thenReturn(new Teachers());
        Teachers teachers = mock(Teachers.class);
        when(teachers.getTeachersID()).thenReturn(1);

        // Act
        Result<Teachers> actualAddResult = this.teachersService.add(teachers);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
        verify(teachers).getTeachersID();
        assertTrue(this.teachersService.findAll().isEmpty());
    }

    @Test
    public void testUpdate() {
        // Arrange
        Teachers teachers = new Teachers();

        // Act
        Result<Teachers> actualUpdateResult = this.teachersService.update(teachers);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate2() {
        // Arrange
        Teachers teachers = null;

        // Act
        Result<Teachers> actualUpdateResult = this.teachersService.update(teachers);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate3() {
        // Arrange
        when(this.teachersRepository.update((Teachers) any())).thenReturn(true);
        Teachers teachers = mock(Teachers.class);
        when(teachers.getTeachersID()).thenReturn(1);

        // Act
        Result<Teachers> actualUpdateResult = this.teachersService.update(teachers);

        // Assert
        assertTrue(actualUpdateResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualUpdateResult.getType());
        verify(this.teachersRepository).update((Teachers) any());
        verify(teachers).getTeachersID();
        assertTrue(this.teachersService.findAll().isEmpty());
    }

    @Test
    public void testUpdate4() {
        // Arrange
        when(this.teachersRepository.update((Teachers) any())).thenReturn(false);
        Teachers teachers = mock(Teachers.class);
        when(teachers.getTeachersID()).thenReturn(1);

        // Act
        Result<Teachers> actualUpdateResult = this.teachersService.update(teachers);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.NOT_FOUND, actualUpdateResult.getType());
        verify(this.teachersRepository).update((Teachers) any());
        verify(teachers, times(2)).getTeachersID();
        assertTrue(this.teachersService.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        when(this.teachersRepository.deleteById(anyInt())).thenReturn(true);
        int teachersID = 1;

        // Act
        boolean actualDeleteByIdResult = this.teachersService.deleteById(teachersID);

        // Assert
        assertTrue(actualDeleteByIdResult);
        verify(this.teachersRepository).deleteById(anyInt());
        assertTrue(this.teachersService.findAll().isEmpty());
    }
}

