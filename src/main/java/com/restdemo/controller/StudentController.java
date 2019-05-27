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
@RequestMapping(value = "/student")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/username")
    public Student getStudentByUsername(@RequestParam Map<String, String> param){
        String username = param.get("username");
        Student student = studentRepository.findOne(username);
        if (student == null){
            throw new ExceptionHandling("Student with username '" + username + "' not found");
        }

        return student;
    }

    @PostMapping("/searchByTeacher")
    public List<Student> getStudentsByTeacher(@RequestParam Map<String, String> param){
        String searchTerm = param.get("teacher");

        List<Student> teacher = studentRepository.findByTeacherContaining(searchTerm);
        if (teacher.isEmpty()){
            throw new ExceptionHandling("Teacher '" + searchTerm + "' not found");
        }

        return teacher;
    }

    @PostMapping("/search")
    public List<Student> searchStudentByNameOrUsername(@RequestParam Map<String, String> param){
        String searchTerm = param.get("text");

        List<Student> student = studentRepository.findByNameContainingOrUsernameContaining(searchTerm, searchTerm);
        if (student.isEmpty()){
            throw new ExceptionHandling("Student '" + searchTerm + "' not found");
        }

        return student;
    }

    @PostMapping("")
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

    @PutMapping("/{username}")
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

    @DeleteMapping("/{username}")
    public boolean deleteStudent(@PathVariable String username){

        Optional<Student> optionalStudent = studentRepository.findByUsernameContaining(username);
        if ( ! optionalStudent.isPresent()){
            throw new ExceptionHandling("Student '" + username + "' not found");
        }

        studentRepository.delete(username);
        return true;
    }
}