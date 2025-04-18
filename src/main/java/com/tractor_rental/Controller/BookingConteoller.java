package com.tractor_rental.Controller;


import com.tractor_rental.Service.BookingService;
import com.tractor_rental.modal.Booking;
import com.tractor_rental.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingConteoller {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/book")
    public Booking createBooking(@RequestBody Booking booking) {
        booking.setStatus("Pending");
        booking.setPaidStatus("Unpaid");
        Booking savedBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(savedBooking).getBody();
    }

//    this for update the payment status
@PutMapping("{id}/payment-status/{status}")
public ResponseEntity<Booking> updatePaymentStatus(
        @PathVariable Long id,
        @PathVariable String status) {
    Booking booking = bookingService.findById(id);
    booking.setPaidStatus(status);
    bookingRepository.save(booking);
    return ResponseEntity.ok(booking);
}

//    this for uopdate the amount and status to the booking
@PutMapping("/{id}")
public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
    Booking existingBooking = bookingRepository.findById(id)
            .orElse(null);

    existingBooking.setAmount(updatedBooking.getAmount());
    existingBooking.setRunningHours(updatedBooking.getRunningHours());
    existingBooking.setStatus(updatedBooking.getStatus());

    Booking savedBooking = bookingRepository.save(existingBooking);
    return ResponseEntity.ok(savedBooking);
}


    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }



}
