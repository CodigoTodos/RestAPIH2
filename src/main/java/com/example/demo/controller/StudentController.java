package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long StudentId) {
        return studentService.getStudentById(StudentId);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long StudentId) {
        return studentService.deleteStudent(StudentId);
    }

    @PutMapping("/students/{id}")
    public  ResponseEntity<Student> updateStudent(@PathVariable("id") Long StudentId,
                                      @Valid @RequestBody Student student) {

        return studentService.updateStudent(StudentId,student);
    }



}
