package org.example.firstproject.repositories;

import org.example.firstproject.modals.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<Users, Integer> {

    public Users findByUsername(String username);

    public Users findByEmail(String email);
}
