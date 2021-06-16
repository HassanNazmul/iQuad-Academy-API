package academy.iquadacademy.models;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public class Module {
    private int modID;
    private String modName;
    private int modCode;
    private int depID;

    public int getModID() {
        return modID;
    }

    public void setModID(int modID) {
        this.modID = modID;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public int getModCode() {
        return modCode;
    }

    public void setModCode(int modCode) {
        this.modCode = modCode;
    }

    public int getDepID() {
        return depID;
    }

    public void setDepID(int depID) {
        this.depID = depID;
    }
}
