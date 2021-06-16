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

import academy.iquadacademy.data.ModuleRepository;
import academy.iquadacademy.models.Module;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ModuleService.class})
@ExtendWith(SpringExtension.class)
public class ModuleServiceTest {
    @MockBean
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleService moduleService;

    @Test
    public void testFindAll() {
        // Arrange
        ArrayList<Module> resultModuleList = new ArrayList<Module>();
        when(this.moduleRepository.findAll()).thenReturn(resultModuleList);

        // Act
        List<Module> actualFindAllResult = this.moduleService.findAll();

        // Assert
        assertSame(resultModuleList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.moduleRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Arrange
        Module resultModule = new Module();
        when(this.moduleRepository.findById(anyInt())).thenReturn(resultModule);
        int modID = 1;

        // Act
        Module actualFindByIdResult = this.moduleService.findById(modID);

        // Assert
        assertSame(resultModule, actualFindByIdResult);
        verify(this.moduleRepository).findById(anyInt());
        assertTrue(this.moduleService.findAll().isEmpty());
    }

    @Test
    public void testAdd() {
        // Arrange
        Module resultModule = new Module();
        when(this.moduleRepository.add((Module) any())).thenReturn(resultModule);
        Module resultModule1 = new Module();

        // Act
        Result<Module> actualAddResult = this.moduleService.add(resultModule1);

        // Assert
        assertTrue(actualAddResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualAddResult.getType());
        assertSame(resultModule, actualAddResult.getPayload());
        verify(this.moduleRepository).add((Module) any());
        assertTrue(this.moduleService.findAll().isEmpty());
    }

    @Test
    public void testAdd2() {
        // Arrange
        when(this.moduleRepository.add((Module) any())).thenReturn(new Module());
        Module resultModule = null;

        // Act
        Result<Module> actualAddResult = this.moduleService.add(resultModule);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
    }

    @Test
    public void testAdd3() {
        // Arrange
        when(this.moduleRepository.add((Module) any())).thenReturn(new Module());
        Module resultModule = mock(Module.class);
        when(resultModule.getModID()).thenReturn(1);

        // Act
        Result<Module> actualAddResult = this.moduleService.add(resultModule);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
        verify(resultModule).getModID();
        assertTrue(this.moduleService.findAll().isEmpty());
    }

    @Test
    public void testUpdate() {
        // Arrange
        Module resultModule = new Module();

        // Act
        Result<Module> actualUpdateResult = this.moduleService.update(resultModule);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate2() {
        // Arrange
        Module resultModule = null;

        // Act
        Result<Module> actualUpdateResult = this.moduleService.update(resultModule);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate3() {
        // Arrange
        when(this.moduleRepository.update((Module) any())).thenReturn(true);
        Module resultModule = mock(Module.class);
        when(resultModule.getModID()).thenReturn(1);

        // Act
        Result<Module> actualUpdateResult = this.moduleService.update(resultModule);

        // Assert
        assertTrue(actualUpdateResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualUpdateResult.getType());
        verify(this.moduleRepository).update((Module) any());
        verify(resultModule).getModID();
        assertTrue(this.moduleService.findAll().isEmpty());
    }

    @Test
    public void testUpdate4() {
        // Arrange
        when(this.moduleRepository.update((Module) any())).thenReturn(false);
        Module resultModule = mock(Module.class);
        when(resultModule.getModID()).thenReturn(1);

        // Act
        Result<Module> actualUpdateResult = this.moduleService.update(resultModule);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.NOT_FOUND, actualUpdateResult.getType());
        verify(this.moduleRepository).update((Module) any());
        verify(resultModule, times(2)).getModID();
        assertTrue(this.moduleService.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        when(this.moduleRepository.deleteById(anyInt())).thenReturn(true);
        int modID = 1;

        // Act
        boolean actualDeleteByIdResult = this.moduleService.deleteById(modID);

        // Assert
        assertTrue(actualDeleteByIdResult);
        verify(this.moduleRepository).deleteById(anyInt());
        assertTrue(this.moduleService.findAll().isEmpty());
    }
}

