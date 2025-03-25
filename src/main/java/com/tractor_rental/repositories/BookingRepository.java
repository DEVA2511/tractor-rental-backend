package com.tractor_rental.repositories;

import com.tractor_rental.modal.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long > {

}
