package org.example.firstproject.services;

import org.example.firstproject.modals.Student;

import java.util.List;

public interface StudentServices {
    void insertStudents(List<Student> students);
    List<Student> getStudentDetailsAccordingToCourse(String course);
}
