package academy.iquadacademy.data;


import academy.iquadacademy.models.Teachers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public interface TeachersRepository {
    List<Teachers> findAll();

    Teachers findById(int teachersID);

    Teachers add(Teachers teachers);

    boolean update(Teachers teachers);

    @Transactional
    boolean deleteById(int teachersID);
}
