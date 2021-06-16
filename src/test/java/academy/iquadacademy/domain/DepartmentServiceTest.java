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

import academy.iquadacademy.data.DepartmentRepository;
import academy.iquadacademy.models.Department;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {DepartmentService.class})
@ExtendWith(SpringExtension.class)
public class DepartmentServiceTest {
    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentService departmentService;

    @Test
    public void testFindAll() {
        // Arrange
        ArrayList<Department> departmentList = new ArrayList<Department>();
        when(this.departmentRepository.findAll()).thenReturn(departmentList);

        // Act
        List<Department> actualFindAllResult = this.departmentService.findAll();

        // Assert
        assertSame(departmentList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(this.departmentRepository).findAll();
    }

    @Test
    public void testFindById() {
        // Arrange
        Department department = new Department();
        when(this.departmentRepository.findById(anyInt())).thenReturn(department);
        int depID = 1;

        // Act
        Department actualFindByIdResult = this.departmentService.findById(depID);

        // Assert
        assertSame(department, actualFindByIdResult);
        verify(this.departmentRepository).findById(anyInt());
        assertTrue(this.departmentService.findAll().isEmpty());
    }

    @Test
    public void testAdd() {
        // Arrange
        Department department = new Department();
        when(this.departmentRepository.add((Department) any())).thenReturn(department);
        Department department1 = new Department();

        // Act
        Result<Department> actualAddResult = this.departmentService.add(department1);

        // Assert
        assertTrue(actualAddResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualAddResult.getType());
        assertSame(department, actualAddResult.getPayload());
        verify(this.departmentRepository).add((Department) any());
        assertTrue(this.departmentService.findAll().isEmpty());
    }

    @Test
    public void testAdd2() {
        // Arrange
        when(this.departmentRepository.add((Department) any())).thenReturn(new Department());
        Department department = null;

        // Act
        Result<Department> actualAddResult = this.departmentService.add(department);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
    }

    @Test
    public void testAdd3() {
        // Arrange
        when(this.departmentRepository.add((Department) any())).thenReturn(new Department());
        Department department = mock(Department.class);
        when(department.getDepID()).thenReturn(1);

        // Act
        Result<Department> actualAddResult = this.departmentService.add(department);

        // Assert
        assertEquals(1, actualAddResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualAddResult.getType());
        verify(department).getDepID();
        assertTrue(this.departmentService.findAll().isEmpty());
    }

    @Test
    public void testUpdate() {
        // Arrange
        Department department = new Department();

        // Act
        Result<Department> actualUpdateResult = this.departmentService.update(department);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate2() {
        // Arrange
        Department department = null;

        // Act
        Result<Department> actualUpdateResult = this.departmentService.update(department);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.INVALID, actualUpdateResult.getType());
    }

    @Test
    public void testUpdate3() {
        // Arrange
        when(this.departmentRepository.update((Department) any())).thenReturn(true);
        Department department = mock(Department.class);
        when(department.getDepID()).thenReturn(1);

        // Act
        Result<Department> actualUpdateResult = this.departmentService.update(department);

        // Assert
        assertTrue(actualUpdateResult.getMessages().isEmpty());
        assertEquals(ResultType.SUCCESS, actualUpdateResult.getType());
        verify(this.departmentRepository).update((Department) any());
        verify(department).getDepID();
        assertTrue(this.departmentService.findAll().isEmpty());
    }

    @Test
    public void testUpdate4() {
        // Arrange
        when(this.departmentRepository.update((Department) any())).thenReturn(false);
        Department department = mock(Department.class);
        when(department.getDepID()).thenReturn(1);

        // Act
        Result<Department> actualUpdateResult = this.departmentService.update(department);

        // Assert
        assertEquals(1, actualUpdateResult.getMessages().size());
        assertEquals(ResultType.NOT_FOUND, actualUpdateResult.getType());
        verify(this.departmentRepository).update((Department) any());
        verify(department, times(2)).getDepID();
        assertTrue(this.departmentService.findAll().isEmpty());
    }

    @Test
    public void testDeleteById() {
        // Arrange
        when(this.departmentRepository.deleteById(anyInt())).thenReturn(true);
        int depID = 1;

        // Act
        boolean actualDeleteByIdResult = this.departmentService.deleteById(depID);

        // Assert
        assertTrue(actualDeleteByIdResult);
        verify(this.departmentRepository).deleteById(anyInt());
        assertTrue(this.departmentService.findAll().isEmpty());
    }
}

