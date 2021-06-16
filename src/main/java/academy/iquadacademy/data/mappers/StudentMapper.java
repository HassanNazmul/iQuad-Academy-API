package academy.iquadacademy.data.mappers;

import academy.iquadacademy.models.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public class StudentMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();

        student.setStudentID(resultSet.getInt("studentID"));
        student.setFirstName(resultSet.getString("firstName"));
        student.setLastName(resultSet.getString("lastName"));
        if (resultSet.getDate("dob") != null) {
            student.setDob(resultSet.getDate("dob").toLocalDate());
        }
        student.setDepID(resultSet.getInt("depID"));

        return student;
    }

}
