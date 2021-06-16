package academy.iquadacademy.data;

import academy.iquadacademy.models.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public interface StudentRepository {
    List<Student> findAll();

    Student findById(int studentID);

    Student add(Student student);

    boolean update(Student student);

    @Transactional
    boolean deleteById(int studentID);
}
