package org.example.firstproject.repositories.impl;

import org.example.firstproject.repositories.StudentDAO;
import org.example.firstproject.modals.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public class StudentDAOImpl implements StudentDAO {
    private final String INSERT_STUDENT_QUERY = "insert into analytics.student (ID, name, gender, course) values (?, ?, ?, ?)";
    private final String GET_STUDENT_QUERY = "select * from analytics.student where course = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void executeInsertStudent(Student student){
        jdbcTemplate.update(INSERT_STUDENT_QUERY, student.getId(), student.getName(), student.getGender(), student.getCourse());
    }

    @Override
    public List<Student> executeGetStudent(String course) {
        return jdbcTemplate.query(GET_STUDENT_QUERY, new Object[]{course}, (rs, rowNum) -> {
            Student student = new Student(rs.getString("name"), rs.getString("gender"), rs.getString("course"), rs.getString("id"));
            return student;
        });
    }
}
