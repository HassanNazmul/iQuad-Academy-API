package academy.iquadacademy.domain;

import academy.iquadacademy.data.DepartmentRepository;
import academy.iquadacademy.models.Department;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
@Service
public class DepartmentService {
    private final DepartmentRepository repository;

    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> findAll() {
        return repository.findAll();
    }

    public Department findById(int depID) {
        return repository.findById(depID);
    }

    public Result<Department> add(Department department) {
        Result<Department> result = validate(department);
        if (!result.isSuccess()) {
            return result;
        }
        if (department.getDepID() != 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        department = repository.add(department);
        result.setPayload(department);
        return result;
    }

    public Result<Department> update(Department department) {
        Result<Department> result = validate(department);
        if (!result.isSuccess()) {
            return result;
        }

        if (department.getDepID() <= 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        if (!repository.update(department)) {
            String msg = String.format("DEPARTMENT ID: %s, NOT FOUND", department.getDepID());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int depID) {
        return repository.deleteById(depID);
    }

    private Result validate(Department department) {
        Result<Department> result = new Result<>();
        if (department == null) {
            result.addMessage("Department Can Not Be Null!", ResultType.INVALID);
        }
        return result;
    }

}
