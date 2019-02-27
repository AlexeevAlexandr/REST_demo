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

    @GetMapping("/student/{id}")
    public Student show(@PathVariable String id){
        return studentRepository.findOne(Integer.parseInt(id));
    }

    @PostMapping("/student/search")
    public List<Student> search(@RequestParam Map<String, String> body){
        String searchTerm = body.get("text");
        return studentRepository.findByNameContainingOrPassportnumberContaining(searchTerm, searchTerm);
    }

    @PostMapping("/student")
    public Student create(@RequestParam Map<String, String> body){
        String name = body.get("name");
        String passportnumber = body.get("passportnumber");
        return studentRepository.save(new Student(name, passportnumber));
    }

    @PutMapping("/student/{id}")
    public Student update(@PathVariable String id, @RequestParam Map<String, String> body){
        Student student = studentRepository.findOne(Integer.parseInt(id));
        student.setName(body.get("name"));
        student.setPassportnumber(body.get("passportNumber"));
        return studentRepository.save(student);
    }

    @DeleteMapping("/student/{id}")
    public boolean delete(@PathVariable String id){
        studentRepository.delete(Integer.parseInt(id));
        return true;
    }
}