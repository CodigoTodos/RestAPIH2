package com.example.demo.service;

import com.example.demo.controller.StudentController;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentsList = studentRepository.findAll();
        if(studentsList.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            for(Student student : studentsList)
            {
                Long id = student.getStudentId();
                student.add(linkTo(methodOn(StudentController.class).getStudentById(id)).withSelfRel());
            }
            return new ResponseEntity<List<Student>>(studentsList, HttpStatus.OK);
        }
    }


    public ResponseEntity<Student> getStudentById(Long StudentId) {
        Optional<Student> student = studentRepository.findById(StudentId);

        if(!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            student.get().add(linkTo(methodOn(StudentController.class).getAllStudents()).withRel("Student List"));
            return new ResponseEntity<Student>(student.get(),HttpStatus.OK);

        }

    }

    public ResponseEntity<Student> saveStudent(Student student) {
        return new ResponseEntity<Student>(studentRepository.save(student),HttpStatus.CREATED);
    }


    public ResponseEntity<?> deleteStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);

        if(!student.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            studentRepository.delete(student.get());
            return new ResponseEntity<Student>(HttpStatus.OK);
        }
    }

    public ResponseEntity<Student> updateStudent(Long studentId, Student student) {
        Optional<Student> s = studentRepository.findById(studentId);

        if(!s.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            student.setStudentId(s.get().getStudentId());
            return new ResponseEntity<Student>(studentRepository.save(student),HttpStatus.OK);
        }
    }


}
