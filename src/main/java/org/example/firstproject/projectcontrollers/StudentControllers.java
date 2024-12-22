package org.example.firstproject.projectcontrollers;

import org.example.firstproject.modals.Student;
import org.example.firstproject.services.StudentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentControllers {
    private final StudentServices studentServices;

    public StudentControllers(StudentServices studentServices){
        this.studentServices = studentServices;
    }

    @PostMapping(value = "/insertStudentDetails", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> insertStudentDetails(@RequestBody List<Student> students){
        studentServices.insertStudents(students);
        return ResponseEntity.ok("Inserted student records!");
    }

    @GetMapping(value = "/getStudentDetails", produces = "application/json")
    public ResponseEntity<List<Student>> getStudentDetails(@RequestParam(value = "course", required = true) String course){
        List<Student> students = studentServices.getStudentDetailsAccordingToCourse(course);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}
