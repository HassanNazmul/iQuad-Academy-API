package academy.iquadacademy.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TeachersTest {
    @Test
    public void testConstructor() {
        // Arrange and Act
        Teachers actualTeachers = new Teachers();
        String firstName = "Jane";
        actualTeachers.setFirstName(firstName);
        String lastName = "Doe";
        actualTeachers.setLastName(lastName);
        int teachersID = 1;
        actualTeachers.setTeachersID(teachersID);

        // Assert
        assertEquals("Jane", actualTeachers.getFirstName());
        assertEquals("Doe", actualTeachers.getLastName());
        assertEquals(1, actualTeachers.getTeachersID());
    }
}

