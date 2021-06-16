package academy.iquadacademy.data.mappers;

import academy.iquadacademy.models.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department department = new Department();

        department.setDepID(resultSet.getInt("depID"));
        department.setDepName(resultSet.getString("depName"));
        department.setDepCode(resultSet.getInt("depCode"));

        return department;
    }
}
