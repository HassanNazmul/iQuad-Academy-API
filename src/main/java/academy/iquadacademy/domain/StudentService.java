package academy.iquadacademy.domain;

import academy.iquadacademy.data.StudentRepository;
import academy.iquadacademy.models.Student;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    public Student findById(int studentID) {
        return repository.findById(studentID);
    }

    public Result<Student> add(Student student) {
        Result<Student> result = validate(student);
        if (!result.isSuccess()) {
            return result;
        }
        if (student.getStudentID() != 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        student = repository.add(student);
        result.setPayload(student);
        return result;
     }

    public Result<Student> update(Student student) {
        Result<Student> result = validate(student);
        if (!result.isSuccess()) {
            return result;
        }

        if (student.getStudentID() <= 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        if (!repository.update(student)) {
            String msg = String.format("STUDENT ID: %s, NOT FOUND", student.getStudentID());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int studentID) {
        return repository.deleteById(studentID);
    }

    private Result validate(Student student) {
        Result<Student> result = new Result<>();
        if (student == null) {
            result.addMessage("Student Can Not Be Null!", ResultType.INVALID);
        }
        return result;
    }
}
