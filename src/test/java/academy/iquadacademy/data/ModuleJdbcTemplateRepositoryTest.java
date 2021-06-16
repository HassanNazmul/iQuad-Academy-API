package academy.iquadacademy.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.iquadacademy.models.Module;

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

@ContextConfiguration(classes = {ModuleJdbcTemplateRepository.class, JdbcTemplate.class})
@ExtendWith(SpringExtension.class)
public class ModuleJdbcTemplateRepositoryTest {
    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ModuleJdbcTemplateRepository moduleJdbcTemplateRepository;

    @Test
    public void testConstructor() {
        // Arrange
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        // Act
        new ModuleJdbcTemplateRepository(jdbcTemplate);

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
        List<Module> actualFindAllResult = this.moduleJdbcTemplateRepository.findAll();

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
        int modID = 1;

        // Act
        Module actualFindByIdResult = this.moduleJdbcTemplateRepository.findById(modID);

        // Assert
        assertNull(actualFindByIdResult);
        verify(this.jdbcTemplate).query(anyString(), (org.springframework.jdbc.core.RowMapper<Object>) any(),
                (Object[]) any());
        assertTrue(this.moduleJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testAdd() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update((PreparedStatementCreator) any(), (KeyHolder) any())).thenAnswer((invocation) -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            keyHolder.getKeyList().add(Collections.singletonMap("id", 1));
            return 1;
        });
        Module resultModule = new Module();

        // Act
        Module actualAddResult = this.moduleJdbcTemplateRepository.add(resultModule);

        // Assert
        assertSame(resultModule, actualAddResult);
        assertEquals(1, actualAddResult.getModID());
        verify(this.jdbcTemplate).update((PreparedStatementCreator) any(), (KeyHolder) any());
        assertTrue(this.moduleJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        Module resultModule = new Module();

        // Act
        boolean actualUpdateResult = this.moduleJdbcTemplateRepository.update(resultModule);

        // Assert
        assertTrue(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.moduleJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(0);
        Module resultModule = new Module();

        // Act
        boolean actualUpdateResult = this.moduleJdbcTemplateRepository.update(resultModule);

        // Assert
        assertFalse(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.moduleJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testUpdate3() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        Module resultModule = mock(Module.class);
        when(resultModule.getModID()).thenReturn(1);
        when(resultModule.getDepID()).thenReturn(1);
        when(resultModule.getModCode()).thenReturn(1);
        when(resultModule.getModName()).thenReturn("foo");

        // Act
        boolean actualUpdateResult = this.moduleJdbcTemplateRepository.update(resultModule);

        // Assert
        assertTrue(actualUpdateResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        verify(resultModule).getDepID();
        verify(resultModule).getModCode();
        verify(resultModule).getModID();
        verify(resultModule).getModName();
        assertTrue(this.moduleJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(1);
        int modID = 1;

        // Act
        boolean actualDeleteByIdResult = this.moduleJdbcTemplateRepository.deleteById(modID);

        // Assert
        assertTrue(actualDeleteByIdResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.moduleJdbcTemplateRepository.findAll().isEmpty());
    }

    @Test
    public void testDeleteById2() throws DataAccessException {
        // Arrange
        when(this.jdbcTemplate.update(anyString(), (Object[]) any())).thenReturn(0);
        int modID = 1;

        // Act
        boolean actualDeleteByIdResult = this.moduleJdbcTemplateRepository.deleteById(modID);

        // Assert
        assertFalse(actualDeleteByIdResult);
        verify(this.jdbcTemplate).update(anyString(), (Object[]) any());
        assertTrue(this.moduleJdbcTemplateRepository.findAll().isEmpty());
    }
}

