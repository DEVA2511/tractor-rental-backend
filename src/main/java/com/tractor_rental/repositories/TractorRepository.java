package com.tractor_rental.repositories;

import com.tractor_rental.modal.Tractor_Registor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TractorRepository extends JpaRepository<Tractor_Registor,Long> {
//    List<Tractor_Registor> findByAvailability(boolean availability);
}
