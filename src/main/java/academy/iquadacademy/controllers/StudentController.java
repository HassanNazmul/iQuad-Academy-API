package academy.iquadacademy.controllers;

import academy.iquadacademy.domain.Result;
import academy.iquadacademy.domain.StudentService;
import academy.iquadacademy.models.Student;
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
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Student> findAll() {
        return service.findAll();
    }

    @GetMapping("/{studentID}")
    public Student findById(@PathVariable int studentID) {
        return service.findById(studentID);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Student student) {
        Result<Student> result = service.add(student);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{studentID}")
    public ResponseEntity<Object> update(@PathVariable int studentID, @RequestBody Student student) {
        if (studentID != student.getStudentID()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Student> result = service.update(student);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{studentID}")
    public ResponseEntity<Void> deleteById(@PathVariable int studentID) {
        if (service.deleteById(studentID)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
