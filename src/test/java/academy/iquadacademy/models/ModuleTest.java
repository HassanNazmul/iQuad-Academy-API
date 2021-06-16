package academy.iquadacademy.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ModuleTest {
    @Test
    public void testConstructor() {
        // Arrange and Act
        Module actualResultModule = new Module();
        int depID = 1;
        actualResultModule.setDepID(depID);
        int modCode = 1;
        actualResultModule.setModCode(modCode);
        int modID = 1;
        actualResultModule.setModID(modID);
        String modName = "Mod Name";
        actualResultModule.setModName(modName);

        // Assert
        assertEquals(1, actualResultModule.getDepID());
        assertEquals(1, actualResultModule.getModCode());
        assertEquals(1, actualResultModule.getModID());
        assertEquals("Mod Name", actualResultModule.getModName());
    }
}

