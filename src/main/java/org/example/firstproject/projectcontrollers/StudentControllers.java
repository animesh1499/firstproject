package org.example.firstproject.projectcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.modals.Student;
import org.example.firstproject.services.StudentServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/student")
public class StudentControllers {
    private final StudentServices studentServices;

    public StudentControllers(StudentServices studentServices){
        this.studentServices = studentServices;
    }

    @PostMapping(value = "/insertStudentDetails", produces = "application/json", consumes = "application/json")
    public ResponseEntity<String> insertStudentDetails(@Valid @RequestBody List<Student> students){
        log.info("Started inserting students for requestBody : {}", students);
        log.info("After Started inserting students for requestBody!");
        studentServices.insertStudents(students);
        log.info("Completed insertion for requestBody : {}", students);
        String message = "Inserted student records!";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping(value = "/getStudentDetails", produces = "application/json")
    public ResponseEntity<List<Student>> getStudentDetails(@RequestParam(value = "course", required = true) String course){
        log.info("Started getting students for course : {}", course);
        List<Student> students = studentServices.getStudentDetailsAccordingToCourse(course);
        log.info("Completed getting students for course : {}", course);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping(value = "/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
