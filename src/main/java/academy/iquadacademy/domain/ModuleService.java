package academy.iquadacademy.domain;

import academy.iquadacademy.data.ModuleRepository;
import academy.iquadacademy.models.Department;
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

    public Module findById(int modID) {
        return repository.findById(modID);
    }

    public Result<Module> add(Module module) {
        Result<Module> result = validate(module);
        if (!result.isSuccess()) {
            return result;
        }
        if (module.getModID() != 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        module = repository.add(module);
        result.setPayload(module);
        return result;
    }

    public Result<Module> update(Module module) {
        Result<Module> result = validate(module);
        if (!result.isSuccess()) {
            return result;
        }

        if (module.getModID() <= 0) {
            result.addMessage("ERROR", ResultType.INVALID);
            return result;
        }

        if (!repository.update(module)) {
            String msg = String.format("MODULE ID: %s, NOT FOUND", module.getModID());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int modID) {
        return repository.deleteById(modID);
    }

    private Result<Module> validate(Module module) {
        Result<Module> result = new Result<>();
        if (module == null) {
            result.addMessage("Module Can Not Be Null!", ResultType.INVALID);
        }
        return result;
    }
}
