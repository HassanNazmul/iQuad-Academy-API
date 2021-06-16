package academy.iquadacademy.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DepartmentTest {
    @Test
    public void testConstructor() {
        // Arrange and Act
        Department actualDepartment = new Department();
        int depCode = 1;
        actualDepartment.setDepCode(depCode);
        int depID = 1;
        actualDepartment.setDepID(depID);
        String depName = "Dep Name";
        actualDepartment.setDepName(depName);

        // Assert
        assertEquals(1, actualDepartment.getDepCode());
        assertEquals(1, actualDepartment.getDepID());
        assertEquals("Dep Name", actualDepartment.getDepName());
    }
}

