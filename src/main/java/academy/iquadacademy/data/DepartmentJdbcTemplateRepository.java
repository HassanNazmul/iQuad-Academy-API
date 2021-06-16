package academy.iquadacademy.data;

import academy.iquadacademy.data.mappers.DepartmentMapper;
import academy.iquadacademy.models.Department;
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
public class DepartmentJdbcTemplateRepository implements DepartmentRepository {

    private final JdbcTemplate jdbcTemplate;

    public DepartmentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Department> findAll() {
        final String sql = "SELECT * FROM department; ";
        return jdbcTemplate.query(sql, new DepartmentMapper());
    }

    @Override
    @Transactional
    public Department findById(int depID) {
        final String sql ="SELECT * FROM department " +
                "WHERE depID = ? ;";

        Department department = jdbcTemplate.query(sql, new DepartmentMapper(), depID).stream().findFirst().orElse(null);

        return department;
    }

    @Override
    public Department add(Department department) {
        final String sql = "INSERT INTO department (depName, depCode) " +
                "VALUES (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, department.getDepName());
            preparedStatement.setInt(2, department.getDepCode());
            return preparedStatement;
        }, keyHolder);

        if (rowsAffected <= 0 ) {
            return null;
        }
        department.setDepID(keyHolder.getKey().intValue());
        return department;
    }

    @Override
    public boolean update(Department department) {

        final String sql = "UPDATE department SET "
                + "depName = ?, "
                + "depCode = ? "
                + "WHERE depID = ?;";

        return jdbcTemplate.update(sql,
                department.getDepName(),
                department.getDepCode(),
                department.getDepID()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int depID) {
        return jdbcTemplate.update("DELETE FROM department WHERE depID = ?;", depID) > 0;
    }
}
