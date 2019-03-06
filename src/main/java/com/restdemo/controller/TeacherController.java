package com.restdemo.controller;

import com.restdemo.model.Teacher;
import com.restdemo.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @GetMapping("/teacher")
    public List<Teacher> getAllTeachers(){
        return teacherRepository.findAll();
    }

    @GetMapping("/teacher/{name}")
    public Teacher getTeacherByName(@PathVariable String name){
        return teacherRepository.findOne(name);
    }

    @PostMapping("/teacher")
    public Teacher createTeacher(@RequestParam Map<String, String> param){
        String name = param.get("name");
        String password = param.get("password");
        return teacherRepository.save(new Teacher(name, password));
    }

    @PutMapping("/teacher/{name}")
    public Teacher updateTeacher(@PathVariable String name, @RequestParam Map<String, String> param){
        String password = param.get("password");
        Teacher teacher = teacherRepository.findOne(name);
        teacher.setPassword(password);
        return teacherRepository.save(teacher);
    }

    @DeleteMapping("/teacher/{name}")
    public boolean deleteTeacher(@PathVariable String name){
        teacherRepository.delete(name);
        return true;
    }
}
