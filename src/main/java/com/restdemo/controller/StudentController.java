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

        Student student = studentRepository.findOne(username);
        if (student == null){
            throw new ExceptionHandling("Student '" + username + "' not found");
        }

        return student;
    }

    @PostMapping("/student/searchByTeacher")
    public List<Student> getStudentsByTeacher(@RequestParam Map<String, String> param){
        String searchTerm = param.get("teacher");

        List<Student> teacher = studentRepository.findByTeacherContaining(searchTerm);
        if (teacher.isEmpty()){
            throw new ExceptionHandling("Teacher '" + searchTerm + "' not found");
        }

        return teacher;
    }

    @PostMapping("/student/search")
    public List<Student> searchStudentByNameOrUsername(@RequestParam Map<String, String> param){
        String searchTerm = param.get("text");

        List<Student> student = studentRepository.findByNameContainingOrUsernameContaining(searchTerm, searchTerm);
        if (student.isEmpty()){
            throw new ExceptionHandling("Teacher '" + searchTerm + "' not found");
        }

        return student;
    }

    @PostMapping("/student")
    public Student createStudent(@RequestParam Map<String, String> param){

        String username = param.get("username");
        String name = param.get("name");
        String teacher = param.get("teacher");

        Optional<Student> student = studentRepository.findByUsernameContaining(username);
        if (student.isPresent()){
            throw new ExceptionHandling("Student '" + username + "' already exist");
        }

        return studentRepository.save(new Student(username, name, teacher));
    }

    @PutMapping("/student/{username}")
    public Student updateStudent(@PathVariable String username, @RequestParam Map<String, String> param){

        Optional<Student> optionalStudent = studentRepository.findByUsernameContaining(username);
        if ( ! optionalStudent.isPresent()){
            throw new ExceptionHandling("Student '" + username + "' not found");
        }

        String name = param.get("name");
        String teacher = param.get("teacher");
        Student student = studentRepository.findOne(username);
        student.setName(name);
        student.setTeacher(teacher);
        return studentRepository.save(student);
    }

    @DeleteMapping("/student/{username}")
    public boolean deleteStudent(@PathVariable String username){

        Optional<Student> optionalStudent = studentRepository.findByUsernameContaining(username);
        if ( ! optionalStudent.isPresent()){
            throw new ExceptionHandling("Student '" + username + "' not found");
        }

        studentRepository.delete(username);
        return true;
    }
}