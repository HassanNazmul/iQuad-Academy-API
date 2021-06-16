package academy.iquadacademy.controllers;

import academy.iquadacademy.domain.Result;
import academy.iquadacademy.domain.TeachersService;
import academy.iquadacademy.models.Student;
import academy.iquadacademy.models.Teachers;
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
@RequestMapping("/teachers")
public class TeachersController {

    private final TeachersService service;

    public TeachersController(TeachersService service) {
        this.service = service;
    }

    @GetMapping
    public List<Teachers> findAll() {
        return service.findAll();
    }

    @GetMapping("/{teachersID}")
    public Teachers findById(@PathVariable int teachersID) {
        return service.findById(teachersID);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Teachers teachers) {
        Result<Teachers> result = service.add(teachers);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{teachersID}")
    public ResponseEntity<Object> update(@PathVariable int teachersID, @RequestBody Teachers teachers) {
        if (teachersID != teachers.getTeachersID()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Teachers> result = service.update(teachers);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{teachersID}")
    public ResponseEntity<Void> deleteById(@PathVariable int teachersID) {
        if (service.deleteById(teachersID)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
