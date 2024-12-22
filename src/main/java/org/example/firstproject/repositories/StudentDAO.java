package org.example.firstproject.repositories;

import org.example.firstproject.modals.Student;

import java.util.List;

public interface StudentDAO {
    void executeInsertStudent(Student student);
    List<Student> executeGetStudent(String course);
}
