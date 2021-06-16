package academy.iquadacademy.domain;

import academy.iquadacademy.data.TeachersRepository;
import academy.iquadacademy.models.Student;
import academy.iquadacademy.models.Teachers;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
@Service
public class TeachersService {

    private final TeachersRepository repository;

    public TeachersService(TeachersRepository repository) {
        this.repository = repository;
    }

    public List<Teachers> findAll() {
        return repository.findAll();
    }

    public Teachers findById(int teachersID) {
        return repository.findById(teachersID);
    }

    public Result<Teachers> add(Teachers teachers) {
        Result<Teachers> result = validate(teachers);
        if (!result.isSuccess()) {
            return result;
        }
        if (teachers.getTeachersID() != 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        teachers = repository.add(teachers);
        result.setPayload(teachers);
        return result;
    }

    private Result validate(Teachers teachers) {
        Result<Teachers> result = new Result<>();
        if (teachers == null) {
            result.addMessage("Teachers Can Not Be Null!", ResultType.INVALID);
        }
        return result;
    }

    public Result<Teachers> update(Teachers teachers) {
        Result<Teachers> result = validate(teachers);
        if (!result.isSuccess()) {
            return result;
        }

        if (teachers.getTeachersID() <= 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        if (!repository.update(teachers)) {
            String msg = String.format("STUDENT ID: %s, NOT FOUND", teachers.getTeachersID());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int teachersID) {
        return repository.deleteById(teachersID);
    }

}
