package academy.iquadacademy.data;

import academy.iquadacademy.data.mappers.StudentMapper;
import academy.iquadacademy.data.mappers.TeachersMapper;
import academy.iquadacademy.models.Student;
import academy.iquadacademy.models.Teachers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
@Repository
public class TeachersJdbcTemplateRepository implements TeachersRepository {

    private final JdbcTemplate jdbcTemplate;

    public TeachersJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Teachers> findAll() {
        final String sql = "SELECT * FROM teachers; ";
        return jdbcTemplate.query(sql, new TeachersMapper());
    }

    @Override
    @Transactional
    public Teachers findById(int studentID) {
        final String sql ="SELECT * FROM teachers " +
                "WHERE teachersID = ? ;";

        Teachers teachers = jdbcTemplate.query(sql, new TeachersMapper(), studentID).stream().findFirst().orElse(null);

        return teachers;
    }

    @Override
    public Teachers add(Teachers teachers) {
        final String sql = "INSERT INTO teachers (firstName, lastName) " +
                "VALUES (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, teachers.getFirstName());
            preparedStatement.setString(2, teachers.getLastName());
            return preparedStatement;
        }, keyHolder);

        if (rowsAffected <= 0 ) {
            return null;
        }
        teachers.setTeachersID(keyHolder.getKey().intValue());
        return teachers;
    }

    @Override
    public boolean update(Teachers teachers) {

        final String sql = "UPDATE teachers SET "
                + "firstName = ?, "
                + "lastName = ? "
                + "WHERE teachersID = ?;";

        return jdbcTemplate.update(sql,
                teachers.getFirstName(),
                teachers.getLastName(),
                teachers.getTeachersID()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int teachersID) {
        return jdbcTemplate.update("DELETE FROM teachers WHERE teachersID = ?;", teachersID) > 0;
    }
}
