package academy.iquadacademy.data;

import academy.iquadacademy.data.mappers.ModuleMapper;
import academy.iquadacademy.models.Module;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
@Repository
public class ModuleJdbcTemplateRepository implements ModuleRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public ModuleJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Module> findAll() {
        final String sql = "SELECT * FROM module; ";
        return jdbcTemplate.query(sql, new ModuleMapper());
    }

    @Override
    @Transactional
    public Module findById(int modID) {
        final String sql = "SELECT * FROM module " +
                "WHERE modID = ? ;";

        Module module = jdbcTemplate.query(sql, new ModuleMapper(), modID).stream().findFirst().orElse(null);

        return module;
    }

    @Override
    public Module add(Module module) {
        final String sql = "INSERT INTO module (modName, modCode, depID) " +
                "VALUES (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, module.getModName());
            preparedStatement.setInt(2, module.getModCode());
            preparedStatement.setInt(3, module.getDepID());
            return preparedStatement;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }
        module.setModID(keyHolder.getKey().intValue());
        return module;
    }

    @Override
    public boolean update(Module module) {

        final String sql = "UPDATE module SET "
                + "modName = ?, "
                + "modCode = ?, "
                + "depId = ? "
                + "WHERE modId = ?;";

        return jdbcTemplate.update(sql,
                module.getModName(),
                module.getModCode(),
                module.getDepID(),
                module.getModID()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int modID) {
        return jdbcTemplate.update("DELETE FROM module WHERE modID = ?;", modID) > 0;
    }
}
