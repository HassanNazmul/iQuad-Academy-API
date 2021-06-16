package academy.iquadacademy.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class StudentTest {
    @Test
    public void testConstructor() {
        // Arrange and Act
        Student actualStudent = new Student();
        int depID = 1;
        actualStudent.setDepID(depID);
        LocalDate ofEpochDayResult = LocalDate.ofEpochDay(1L);
        actualStudent.setDob(ofEpochDayResult);
        String firstName = "Jane";
        actualStudent.setFirstName(firstName);
        String lastName = "Doe";
        actualStudent.setLastName(lastName);
        int studentID = 1;
        actualStudent.setStudentID(studentID);

        // Assert
        assertEquals(1, actualStudent.getDepID());
        assertSame(ofEpochDayResult, actualStudent.getDob());
        assertEquals("Jane", actualStudent.getFirstName());
        assertEquals("Doe", actualStudent.getLastName());
        assertEquals(1, actualStudent.getStudentID());
    }
}

