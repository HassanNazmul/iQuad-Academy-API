package academy.iquadacademy.data;

import academy.iquadacademy.models.Department;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public interface DepartmentRepository {
    List<Department> findAll();

    Department findById(int depID);

    Department add(Department department);

    boolean update(Department department);

    @Transactional
    boolean deleteById(int depID);
}
