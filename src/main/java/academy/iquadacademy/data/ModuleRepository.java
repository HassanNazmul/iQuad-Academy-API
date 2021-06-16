package academy.iquadacademy.data;

import academy.iquadacademy.models.Module;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
public interface ModuleRepository {

    List<Module> findAll();

//    Module findById(int modID);
//
//    Module add(Module module);
//
//    boolean update(Module module);
//
//    @Transactional
//    boolean deleteById(int modID);
}
