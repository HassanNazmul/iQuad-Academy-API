package academy.iquadacademy.controllers;


import academy.iquadacademy.domain.ModuleService;
import academy.iquadacademy.domain.Result;
import academy.iquadacademy.models.Department;
import academy.iquadacademy.models.Module;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author NAHID
 * Date  16,  June, 2021
 **/
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/module")
public class ModuleController {

    private final ModuleService service;

    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Module> findAll() {
        return service.findAll();
    }

    @GetMapping("/{modID}")
    public Module findById(@PathVariable int modID) {
        return service.findById(modID);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Module module) {
        Result<Module> result = service.add(module);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{modID}")
    public ResponseEntity<Object> update(@PathVariable int modID, @RequestBody Module module) {
        if (modID != module.getModID()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Module> result = service.update(module);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{modID}")
    public ResponseEntity<Void> deleteById(@PathVariable int modID) {
        if (service.deleteById(modID)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
