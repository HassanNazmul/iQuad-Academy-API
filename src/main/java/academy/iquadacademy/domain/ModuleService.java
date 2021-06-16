package academy.iquadacademy.domain;

import academy.iquadacademy.data.ModuleRepository;
import academy.iquadacademy.models.Module;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
@Service
public class ModuleService {

    private final ModuleRepository repository;

    public ModuleService(ModuleRepository repository) {
        this.repository = repository;
    }

    public List<Module> findAll() {
        return repository.findAll();
    }
}
