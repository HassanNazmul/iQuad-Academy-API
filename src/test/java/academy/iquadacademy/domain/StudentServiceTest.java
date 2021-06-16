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

import academy.iquadacademy.data.StudentRepository;
import academy.iquadacademy.models.Student;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentService.class})
@ExtendWith(SpringExtension.class)
public class StudentServiceTest {
    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Test
    public void testFindAll() {
        // Arrange
        ArrayList<Student> studentList = new ArrayList<Student>();
        when(this.studentRepository.findAll()).thenReturn(studentList);

        // Act
        List<Student> actualFindAllResult = this.studentService.findAll();

        // Assert
        assertSame(studentList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.studentRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Arrange
        Student student = new Student();
        when(this.studentRepository.findById(anyInt())).thenReturn(student);
        int studentID = 1;

        // Act
        Student actualFindByIdResult = this.studentService.findById(studentID);

        // Assert
        assertSame(student, actualFindByIdResult);
        verify(this.studentRepository).findById(anyInt());
        assertTrue(this.studentService.findAll().isEmpty());
    }

    @Test
    public void testAdd() {
        // Arrange
        Student student = new Student();
        when(this.studentRepository.add((Student) any())).thenReturn(student);
        Student student1 = new Student();

        // Act
        Result<Student> actualAddResult = this.studentService.add(student1);

        // Assert
        assertTrue(actualAddResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualAddResult.getType());
        assertSame(student, actualAddResult.getPayload());
        verify(this.studentRepository).add((Student) any());
        assertTrue(this.studentService.findAll().isEmpty());
    }

    @Test
    public void testAdd2() {
        // Arrange
        when(this.studentRepository.add((Student) any())).thenReturn(new Student());
        Student student = null;

        // Act
        Result<Student> actualAddResult = this.studentService.add(student);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
    }

    @Test
    public void testAdd3() {
        // Arrange
        when(this.studentRepository.add((Student) any())).thenReturn(new Student());
        Student student = mock(Student.class);
        when(student.getStudentID()).thenReturn(1);

        // Act
        Result<Student> actualAddResult = this.studentService.add(student);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
        verify(student).getStudentID();
        assertTrue(this.studentService.findAll().isEmpty());
    }

    @Test
    public void testUpdate() {
        // Arrange
        Student student = new Student();

        // Act
        Result<Student> actualUpdateResult = this.studentService.update(student);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate2() {
        // Arrange
        Student student = null;

        // Act
        Result<Student> actualUpdateResult = this.studentService.update(student);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate3() {
        // Arrange
        when(this.studentRepository.update((Student) any())).thenReturn(true);
        Student student = mock(Student.class);
        when(student.getStudentID()).thenReturn(1);

        // Act
        Result<Student> actualUpdateResult = this.studentService.update(student);

        // Assert
        assertTrue(actualUpdateResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualUpdateResult.getType());
        verify(this.studentRepository).update((Student) any());
        verify(student).getStudentID();
        assertTrue(this.studentService.findAll().isEmpty());
    }

    @Test
    public void testUpdate4() {
        // Arrange
        when(this.studentRepository.update((Student) any())).thenReturn(false);
        Student student = mock(Student.class);
        when(student.getStudentID()).thenReturn(1);

        // Act
        Result<Student> actualUpdateResult = this.studentService.update(student);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.NOT_FOUND, actualUpdateResult.getType());
        verify(this.studentRepository).update((Student) any());
        verify(student, times(2)).getStudentID();
        assertTrue(this.studentService.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        when(this.studentRepository.deleteById(anyInt())).thenReturn(true);
        int studentID = 1;

        // Act
        boolean actualDeleteByIdResult = this.studentService.deleteById(studentID);

        // Assert
        assertTrue(actualDeleteByIdResult);
        verify(this.studentRepository).deleteById(anyInt());
        assertTrue(this.studentService.findAll().isEmpty());
    }
}

