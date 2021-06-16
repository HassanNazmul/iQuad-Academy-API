package academy.iquadacademy.data.mappers;

import academy.iquadacademy.models.Teachers;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public class TeachersMapper implements RowMapper<Teachers> {

    @Override
    public Teachers mapRow(ResultSet resultSet, int i) throws SQLException {
        Teachers teachers = new Teachers();

        teachers.setTeachersID(resultSet.getInt("teachersID"));
        teachers.setFirstName(resultSet.getString("firstName"));
        teachers.setLastName(resultSet.getString("lastName"));

        return teachers;
    }
}
