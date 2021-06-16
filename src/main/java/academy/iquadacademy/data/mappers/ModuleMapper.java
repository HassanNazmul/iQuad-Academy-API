package academy.iquadacademy.data.mappers;

import academy.iquadacademy.models.Module;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public class ModuleMapper implements RowMapper<Module> {

    @Override
    public Module mapRow(ResultSet resultSet, int i) throws SQLException {
        Module module = new Module();

        module.setModID(resultSet.getInt("modID"));
        module.setModName(resultSet.getString("modName"));
        module.setModCode(resultSet.getInt("modCode"));
        module.setDepID(resultSet.getInt("depID"));

        return module;
    }
}
