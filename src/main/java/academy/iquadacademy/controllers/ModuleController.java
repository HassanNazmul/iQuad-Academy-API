package academy.iquadacademy.controllers;


import academy.iquadacademy.domain.ModuleService;
import academy.iquadacademy.models.Module;
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
}
