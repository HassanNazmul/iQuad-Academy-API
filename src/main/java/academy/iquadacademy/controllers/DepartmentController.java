package academy.iquadacademy.controllers;

import academy.iquadacademy.domain.DepartmentService;
import academy.iquadacademy.domain.Result;
import academy.iquadacademy.models.Department;
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
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Department> findAll() {
        return service.findAll();
    }

    @GetMapping("/{depID}")
    public Department findById(@PathVariable int depID) {
        return service.findById(depID);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Department department) {
        Result<Department> result = service.add(department);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{depID}")
    public ResponseEntity<Object> update(@PathVariable int depID, @RequestBody Department department) {
        if (depID != department.getDepID()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Department> result = service.update(department);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{depID}")
    public ResponseEntity<Void> deleteById(@PathVariable int depID) {
        if (service.deleteById(depID)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
