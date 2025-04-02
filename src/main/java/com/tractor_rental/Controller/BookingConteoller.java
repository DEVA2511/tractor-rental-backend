package com.tractor_rental.Controller;


import com.tractor_rental.Service.BookingService;
import com.tractor_rental.modal.Booking;
import com.tractor_rental.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class BookingConteoller {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/book")
    public Booking createBooking(@RequestBody Booking booking) {
        booking.setStatus("Pending");
        Booking savedBooking = bookingRepository.save(booking);
        return ResponseEntity.ok(savedBooking).getBody();
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

//    @PutMapping("/{id}")
//    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
//        return bookingService.updateBooking(id, booking);
//    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }


}
