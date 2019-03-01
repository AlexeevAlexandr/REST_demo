package com.restdemo.controller;

import com.restdemo.model.Student;
import com.restdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student")
    public List<Student> index(){
        return studentRepository.findAll();
    }

    @GetMapping("/student/{username}")
    public Student show(@PathVariable String username){
        return studentRepository.findOne(username);
    }

    @PostMapping("/student/search")
    public List<Student> search(@RequestParam Map<String, String> body){
        String searchTerm = body.get("text");
        return studentRepository.findByNameContainingOrPasswordContaining(searchTerm, searchTerm);
    }

    @PostMapping("/student")
    public Student create(@RequestParam Map<String, String> body){
        String username = body.get("username");
        String name = body.get("name");
        String password = body.get("password");
        return studentRepository.save(new Student(username, name, password));
    }

    @PutMapping("/student/{username}")
    public Student update(@PathVariable String username, @RequestParam Map<String, String> body){
        String name = body.get("name");
        String password = body.get("password");
        Student student = studentRepository.findOne(username);
        student.setName(name);
        student.setPassword(password);
        return studentRepository.save(student);
    }

    @DeleteMapping("/student/{username}")
    public boolean delete(@PathVariable String username){
        studentRepository.delete(username);
        return true;
    }
}