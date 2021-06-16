package academy.iquadacademy.data;

import academy.iquadacademy.data.mappers.ModuleMapper;
import academy.iquadacademy.models.Module;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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


}
