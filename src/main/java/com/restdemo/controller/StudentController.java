package com.restdemo.controller;

import com.restdemo.exceptionHandling.ExceptionHandling;
import com.restdemo.model.Student;
import com.restdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping(value = "", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping(value = "/id/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
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

    @GetMapping(value = "/teacher/{teacher}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Student> getStudentsByTeacher(@PathVariable String teacher){

        List<Student> studentList = studentRepository.findByTeacherContaining(teacher);
        if (studentList.isEmpty()){
            throw new ExceptionHandling("Teacher '" + teacher + "' not found");
        }

        return studentList;
    }

    @GetMapping(value = "/search/{name}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public List<Student> searchStudentByNameOrUsername(@PathVariable String name){

        if (name.isEmpty()){
            throw new ExceptionHandling("Name not specified");
        }
        List<Student> student = studentRepository.findByFirstNameContainingOrLastNameContaining(name, name);
        if (student.isEmpty()){
            throw new ExceptionHandling("Student '" + name + "' not found");
        }

        return student;
    }

    @PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Student createStudent(@RequestBody Student student){

        if (studentRepository.findByEmailContaining(student.getEmail()) != null){
            throw new ExceptionHandling("Student with this email '" + student.getEmail() + "' already exist");
        }

        return studentRepository.save(student);
    }

    @PutMapping(value = "/update", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
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