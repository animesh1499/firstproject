package org.example.firstproject.modals;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    /*private String email;
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    public enum AuthProvider{
        LOCAL, GOOGLE
    }*/
}
