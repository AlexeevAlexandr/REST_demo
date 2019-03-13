package com.restdemo.controller;

import com.restdemo.exceptionHandling.ExceptionHandling;
import com.restdemo.model.Student;
import com.restdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/student/{username}")
    public Student getStudentByUsername(@PathVariable String username){
        return studentRepository.findOne(username);
    }

    @PostMapping("/student/searchByTeacher")
    public List<Student> getStudentsByTeacher(@RequestParam Map<String, String> param){
        String searchTerm = param.get("teacher");
        return studentRepository.findByTeacherContaining(searchTerm);
    }

    @PostMapping("/student/search")
    public List<Student> searchStudentByNameOrUsername(@RequestParam Map<String, String> param){
        String searchTerm = param.get("text");
        return studentRepository.findByNameContainingOrUsernameContaining(searchTerm, searchTerm);
    }

    @PostMapping("/student")
    public Student createStudent(@RequestParam Map<String, String> param){

        String username = param.get("username");
        String name = param.get("name");
        String teacher = param.get("teacher");

        Optional<Student> student = studentRepository.findByUsernameContaining(username);
        if ( ! student.isPresent()){
            throw new ExceptionHandling("Student with username '" + username + "' already exist");
        }

        return studentRepository.save(new Student(username, name, teacher));
    }

    @PutMapping("/student/{username}")
    public Student updateStudent(@PathVariable String username, @RequestParam Map<String, String> param){
        String name = param.get("name");
        String teacher = param.get("teacher");
        Student student = studentRepository.findOne(username);
        student.setName(name);
        student.setTeacher(teacher);
        return studentRepository.save(student);
    }

    @DeleteMapping("/student/{username}")
    public boolean deleteStudent(@PathVariable String username){
        studentRepository.delete(username);
        return true;
    }
}