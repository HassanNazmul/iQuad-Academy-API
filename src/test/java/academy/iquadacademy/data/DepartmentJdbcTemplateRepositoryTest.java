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

import academy.iquadacademy.models.Department;

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

@ContextConfiguration(classes = {DepartmentJdbcTemplateRepository.class, JdbcTemplate.class})
@ExtendWith(SpringExtension.class)
public class DepartmentJdbcTemplateRepositoryTest {
    @Autowired
    private DepartmentJdbcTemplateRepository departmentJdbcTemplateRepository;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testConstructor() {
        // Arrange
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        // Act
        new DepartmentJdbcTemplateRepository(jdbcTemplate);

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
        List<Department> actualFindAllResult = this.departmentJdbcTemplateRepository.findAll();

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
        int depID = 1;

        // Act
        Department actualFindByIdResult = this.departmentJdbcTemplateRepository.findById(depID);

        // Assert
        assertNull(actualFindByIdResult);
        verify(this.jdbcTemplate).query(anyString(), (org.springframework.jdbc.core.RowMapper<Object>) any(),
                (Object[]) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((PreparedStatementCreator) any(), (KeyHolder) any())).thenAnswer((invocation) -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
            return 1;
        });
        Department department = new Department();

        // Act
        Department actualAddResult = this.departmentJdbcTemplateRepository.add(department);

        // Assert
        assertSame(department, actualAddResult);
        assertEquals(1, actualAddResult.getDepID());
        verify(this.jdbcTemplate).update((PreparedStatementCreator) any(), (KeyHolder) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((PreparedStatementCreator) any(), (KeyHolder) any())).thenAnswer((invocation) -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 0));
            return 1;
        });
        Department department = new Department();

        // Act
        Department actualAddResult = this.departmentJdbcTemplateRepository.add(department);

        // Assert
        assertSame(department, actualAddResult);
        assertEquals(0, actualAddResult.getDepID());
        verify(this.jdbcTemplate).update((PreparedStatementCreator) any(), (KeyHolder) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd3() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((PreparedStatementCreator) any(), (KeyHolder) any())).thenAnswer((invocation) -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
            return 1;
        });
        Department department = mock(Department.class);
        doNothing().when(department).setDepID(anyInt());

        // Act
        this.departmentJdbcTemplateRepository.add(department);

        // Assert
        verify(this.jdbcTemplate).update((PreparedStatementCreator) any(), (KeyHolder) any());
        verify(department).setDepID(anyInt());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd4() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((org.springframework.jdbc.core.PreparedStatementCreator) any(),
                (org.springframework.jdbc.support.KeyHolder) any())).thenReturn(0);
        Department department = new Department();

        // Act
        Department actualAddResult = this.departmentJdbcTemplateRepository.add(department);

        // Assert
        assertNull(actualAddResult);
        verify(this.jdbcTemplate).update((org.springframework.jdbc.core.PreparedStatementCreator) any(),
                (org.springframework.jdbc.support.KeyHolder) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        Department department = new Department();

        // Act
        boolean actualUpdateResult = this.departmentJdbcTemplateRepository.update(department);

        // Assert
        assertTrue(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(0);
        Department department = new Department();

        // Act
        boolean actualUpdateResult = this.departmentJdbcTemplateRepository.update(department);

        // Assert
        assertFalse(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate3() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        Department department = mock(Department.class);
        when(department.getDepID()).thenReturn(1);
        when(department.getDepCode()).thenReturn(1);
        when(department.getDepName()).thenReturn("foo");

        // Act
        boolean actualUpdateResult = this.departmentJdbcTemplateRepository.update(department);

        // Assert
        assertTrue(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        verify(department).getDepCode();
        verify(department).getDepID();
        verify(department).getDepName();
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        int depID = 1;

        // Act
        boolean actualDeleteByIdResult = this.departmentJdbcTemplateRepository.deleteById(depID);

        // Assert
        assertTrue(actualDeleteByIdResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testDeleteById2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(0);
        int depID = 1;

        // Act
        boolean actualDeleteByIdResult = this.departmentJdbcTemplateRepository.deleteById(depID);

        // Assert
        assertFalse(actualDeleteByIdResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.departmentJdbcTemplateRepository.findAll().isEmpty());
    }
}

