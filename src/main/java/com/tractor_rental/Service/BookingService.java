package com.tractor_rental.Service;

import com.tractor_rental.modal.Booking;
import com.tractor_rental.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking updateBooking(Long id, Booking updatedBooking) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setDriverName(updatedBooking.getDriverName());
            booking.setPhoneNumber(updatedBooking.getPhoneNumber());
            booking.setLocation(updatedBooking.getLocation());
            booking.setRentalHours(updatedBooking.getRentalHours());
            booking.setDate(updatedBooking.getDate());
            booking.setTime(updatedBooking.getTime());
            return bookingRepository.save(booking);
        }).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}
