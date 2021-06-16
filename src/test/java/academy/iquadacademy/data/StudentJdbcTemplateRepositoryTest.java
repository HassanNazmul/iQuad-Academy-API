package academy.iquadacademy.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.iquadacademy.models.Student;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StudentJdbcTemplateRepository.class, JdbcTemplate.class})
@ExtendWith(SpringExtension.class)
public class StudentJdbcTemplateRepositoryTest {
    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentJdbcTemplateRepository studentJdbcTemplateRepository;

    @Test
    public void testConstructor() {
        // Arrange
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        // Act
        new StudentJdbcTemplateRepository(jdbcTemplate);

        // Assert
        assertNull(jdbcTemplate.getDataSource());
        assertFalse(jdbcTemplate.isSkipUndeclaredResults());
        assertFalse(jdbcTemplate.isSkipResultsProcessing());
        assertFalse(jdbcTemplate.isResultsMapCaseInsensitive());
        assertTrue(jdbcTemplate.isLazyInit());
        assertTrue(jdbcTemplate.isIgnoreWarnings());
        assertEquals(-1, jdbcTemplate.getQueryTimeout());
        assertEquals(-1, jdbcTemplate.getMaxRows());
        assertEquals(-1, jdbcTemplate.getFetchSize());
        SQLExceptionTranslator exceptionTranslator = jdbcTemplate.getExceptionTranslator();
        assertTrue(exceptionTranslator instanceof SQLStateSQLExceptionTranslator);
        assertNull(((SQLStateSQLExceptionTranslator) exceptionTranslator).getFallbackTranslator());
    }

    @Test
    public void testFindAll() throws DataAccessException {
        // Arrange
        ArrayList<Object> objectList = new ArrayList<Object>();
        when(this.jdbcTemplate.query(anyString(), (org.springframework.jdbc.core.RowMapper<Object>) any()))
                .thenReturn(objectList);

        // Act
        List<Student> actualFindAllResult = this.studentJdbcTemplateRepository.findAll();

        // Assert
        assertSame(objectList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.jdbcTemplate).query(anyString(), (org.springframework.jdbc.core.RowMapper<Object>) any());
    }

    @Test
    public void testFindById() throws DataAccessException {
        // Arrange
        when(
                this.jdbcTemplate.query(anyString(), (org.springframework.jdbc.core.RowMapper<Object>) any(), (Object[]) any()))
                .thenReturn(new ArrayList<Object>());
        int studentID = 1;

        // Act
        Student actualFindByIdResult = this.studentJdbcTemplateRepository.findById(studentID);

        // Assert
        assertNull(actualFindByIdResult);
        verify(this.jdbcTemplate).query(anyString(), (org.springframework.jdbc.core.RowMapper<Object>) any(),
                (Object[]) any());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((PreparedStatementCreator) any(), (KeyHolder) any())).thenAnswer((invocation) -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
            return 1;
        });
        Student student = new Student();

        // Act
        Student actualAddResult = this.studentJdbcTemplateRepository.add(student);

        // Assert
        assertSame(student, actualAddResult);
        assertEquals(1, actualAddResult.getStudentID());
        verify(this.jdbcTemplate).update((PreparedStatementCreator) any(), (KeyHolder) any());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((PreparedStatementCreator) any(), (KeyHolder) any())).thenAnswer((invocation) -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
            return 1;
        });
        Student student = mock(Student.class);
        doNothing().when(student).setStudentID(anyInt());

        // Act
        this.studentJdbcTemplateRepository.add(student);

        // Assert
        verify(this.jdbcTemplate).update((PreparedStatementCreator) any(), (KeyHolder) any());
        verify(student).setStudentID(anyInt());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd3() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((org.springframework.jdbc.core.PreparedStatementCreator) any(),
                (org.springframework.jdbc.support.KeyHolder) any())).thenReturn(0);
        Student student = new Student();

        // Act
        Student actualAddResult = this.studentJdbcTemplateRepository.add(student);

        // Assert
        assertNull(actualAddResult);
        verify(this.jdbcTemplate).update((org.springframework.jdbc.core.PreparedStatementCreator) any(),
                (org.springframework.jdbc.support.KeyHolder) any());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        Student student = new Student();

        // Act
        boolean actualUpdateResult = this.studentJdbcTemplateRepository.update(student);

        // Assert
        assertTrue(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(0);
        Student student = new Student();

        // Act
        boolean actualUpdateResult = this.studentJdbcTemplateRepository.update(student);

        // Assert
        assertFalse(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate3() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        Student student = mock(Student.class);
        when(student.getStudentID()).thenReturn(1);
        when(student.getDepID()).thenReturn(1);
        when(student.getDob()).thenReturn(LocalDate.ofEpochDay(1L));
        when(student.getLastName()).thenReturn("foo");
        when(student.getFirstName()).thenReturn("foo");

        // Act
        boolean actualUpdateResult = this.studentJdbcTemplateRepository.update(student);

        // Assert
        assertTrue(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        verify(student).getDepID();
        verify(student).getDob();
        verify(student).getFirstName();
        verify(student).getLastName();
        verify(student).getStudentID();
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        int studentID = 1;

        // Act
        boolean actualDeleteByIdResult = this.studentJdbcTemplateRepository.deleteById(studentID);

        // Assert
        assertTrue(actualDeleteByIdResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testDeleteById2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(0);
        int studentID = 1;

        // Act
        boolean actualDeleteByIdResult = this.studentJdbcTemplateRepository.deleteById(studentID);

        // Assert
        assertFalse(actualDeleteByIdResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.studentJdbcTemplateRepository.findAll().isEmpty());
    }
}

