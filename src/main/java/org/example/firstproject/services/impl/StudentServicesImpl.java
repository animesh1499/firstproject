package org.example.firstproject.services.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.example.firstproject.exceptions.StudentException;
import org.example.firstproject.modals.Student;
import org.example.firstproject.repositories.StudentDAO;
import org.example.firstproject.services.StudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class StudentServicesImpl implements StudentServices {

    @Autowired
    private StudentDAO studentDAO;
    @Override
    @Transactional
    public void insertStudents(List<Student> students) {
        try{
            for(Student student : students){
                studentDAO.executeInsertStudent(student);
            }
        }catch(DataAccessException e){
            log.error("Error inserting records with exception: {}", e.getStackTrace());
            throw new StudentException("There was error inserting students in DB!");
        }catch (Exception e){
            log.error("Error inserting records with exception: {}", e.getStackTrace());
            throw new StudentException(e.getMessage());
        }
    }

    @Override
    public List<Student> getStudentDetailsAccordingToCourse(String course) {
        try {
            return studentDAO.executeGetStudent(course);
        }catch (DataAccessException e){
            log.error("Error getting records with exception: {}", e.getStackTrace());
            throw new StudentException("There was error getting students from DB!");
        }catch(Exception ex){
            log.error("Error getting records with exception: {}", ex.getStackTrace());
            throw new StudentException(ex.getMessage());
        }
    }
}
