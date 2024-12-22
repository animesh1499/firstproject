package org.example.firstproject.modals;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private String name;
    private String gender;
    @NotNull(message = "Course must not be null!")
    @Size(min = 3 , max = 15, message = "The course name should be of 3 to 15 characters!")
    private String course;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private int id;
}
