package academy.iquadacademy.data;

import academy.iquadacademy.data.mappers.StudentMapper;
import academy.iquadacademy.models.Student;
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
public class StudentJdbcTemplateRepository implements StudentRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Student> findAll() {
        final String sql = "SELECT * FROM student; ";
        return jdbcTemplate.query(sql, new StudentMapper());
    }

    @Override
    @Transactional
    public Student findById(int studentID) {
        final String sql ="SELECT * FROM student " +
                "WHERE studentID = ? ;";

        Student student = jdbcTemplate.query(sql, new StudentMapper(), studentID).stream().findFirst().orElse(null);

        return student;
    }

    @Override
    public Student add(Student student) {
        final String sql = "INSERT INTO student (firstName, lastName, dob, depID) " +
                "VALUES (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setDate(3, student.getDob() == null ? null : Date.valueOf(student.getDob()));
            preparedStatement.setInt(4, student.getDepID());
            return preparedStatement;
        }, keyHolder);

        if (rowsAffected <= 0 ) {
            return null;
        }
        student.setStudentID(keyHolder.getKey().intValue());
        return student;
    }

    @Override
    public boolean update(Student student) {

        final String sql = "UPDATE student SET "
                + "firstName = ?, "
                + "lastName = ?, "
                + "dob = ?, "
                + "depID = ? "
                + "WHERE studentID = ?;";

        return jdbcTemplate.update(sql,
                student.getFirstName(),
                student.getLastName(),
                student.getDob(),
                student.getDepID(),
                student.getStudentID()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int studentID) {
        jdbcTemplate.update("DELETE FROM roster WHERE studentID = ?;", studentID);
       return jdbcTemplate.update("DELETE FROM student WHERE studentID = ?;", studentID) > 0;
    }
}
