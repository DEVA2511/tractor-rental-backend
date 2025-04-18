package com.tractor_rental.repositories;

import com.tractor_rental.modal.Booking;
import com.tractor_rental.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
