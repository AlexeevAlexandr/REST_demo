package com.restdemo.controller;

import com.restdemo.exceptionHandling.ExceptionHandling;
import com.restdemo.model.Student;
import com.restdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("{id}")
    public Student getStudentByUsername(@PathVariable String id){
        Student student;

        try {
            student = studentRepository.findOne(Integer.parseInt(id));
        }catch (NumberFormatException e){
            throw new ExceptionHandling("Incorrect id '" + id + "', id must be integer");
        }

        if (student == null){
            throw new ExceptionHandling("Student with id '" + id + "' not found");
        }

        return student;
    }

    @GetMapping("{teacher}")
    public List<Student> getStudentsByTeacher(@PathVariable String teacher){

        List<Student> studentList = studentRepository.findByTeacherContaining(teacher);
        if (teacher.isEmpty()){
            throw new ExceptionHandling("Teacher '" + teacher + "' not found");
        }

        return studentList;
    }

    @GetMapping("/search")
    public List<Student> searchStudentByNameOrUsername(@RequestParam Map<String, String> param){
        String name = param.get("text");

        List<Student> student = studentRepository.findByFirstNameContainingOrLastNameContaining(name, name);
        if (student.isEmpty()){
            throw new ExceptionHandling("Student '" + name + "' not found");
        }

        return student;
    }

    @PostMapping("/create")
    public Student createStudent(@RequestParam Map<String, String> param){

        String email = param.get("email");

        Student student = studentRepository.findByEmailContaining(email);
        if (student == null){
            throw new ExceptionHandling("Student with this email '" + email + "' already exist");
        }

        String firstName = param.get("firstName");
        String lastName = param.get("lastName");
        String gender = param.get("gender");
        String ip_address = param.get("ip_address");
        String teacher = param.get("teacher");

        return studentRepository.save(new Student(firstName, lastName, email, gender, ip_address, teacher));
    }

    @PutMapping("/update")
    public Student updateStudent(@RequestParam Map<String, String> param){

        String email = param.get("email");

        Student student = studentRepository.findByEmailContaining(email);
        if (student == null){
            throw new ExceptionHandling("Student with this email '" + email + "' not found");
        }

        String firstName = param.get("firstName");
        String lastName = param.get("lastName");
        String gender = param.get("gender");
        String ip_address = param.get("ip_address");
        String teacher = param.get("teacher");

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setGender(gender);
        student.setIpAddress(ip_address);
        student.setTeacher(teacher);

        return studentRepository.save(student);
    }

    @DeleteMapping("/{id}")
    public boolean deleteStudent(@PathVariable int id){

        Student student = studentRepository.findOne(id);
        if (student == null){
            throw new ExceptionHandling("Student with id '" + id + "' not found");
        }

        studentRepository.delete(student);
        return true;
    }
}