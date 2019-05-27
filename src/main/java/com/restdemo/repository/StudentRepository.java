package com.restdemo.repository;

import com.restdemo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByFirstNameContainingOrLastNameContaining(String text, String textAgain);

    List<Student> findByTeacherContaining(String text);

    Student findByEmailContaining(String text);
}