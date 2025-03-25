package com.tractor_rental.Controller;

import com.tractor_rental.Service.TractorService;
import com.tractor_rental.modal.Tractor_Registor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/tractors")
public class TractorController {
    @Autowired
    private TractorService tractorService;

    @GetMapping
    public List<Tractor_Registor> getAllTractors() {
        return tractorService.getAllTractors();
    }

    @GetMapping("/tractor/{id}")
    public ResponseEntity<Tractor_Registor> getTractorById(@PathVariable Long id) {
        return ResponseEntity.ok(tractorService.getTractorById(id));
    }

    @PostMapping(value = "/register", consumes = {"multipart/form-data"})
    public ResponseEntity<String> registerTractor(
            @RequestParam("driverName") String driverName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("tractorName") String tractorName,
            @RequestParam("model") String model,
            @RequestParam("year") int year,
            @RequestParam("rentalPrice") double rentalPrice,
            @RequestParam("location") String location,
            @RequestParam("licenseNumber") String licenseNumber,
            @RequestParam("availability") boolean availability,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        try {
            Tractor_Registor tractor = new Tractor_Registor();
            tractor.setDriverName(driverName);
            tractor.setPhoneNumber(phoneNumber);
            tractor.setTractorName(tractorName);
            tractor.setModel(model);
            tractor.setYear(year);
            tractor.setRentalPrice(rentalPrice);
            tractor.setLocation(location);
            tractor.setLicenseNumber(licenseNumber);
            tractor.setAvailability(availability);

            if (image != null && !image.isEmpty()) {
                tractor.setImage(image.getBytes());
            }
            tractorService.saveTractor(tractor);
            return ResponseEntity.ok("Tractor Registered Successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Tractor_Registor> updateTractor(@PathVariable Long id, @RequestBody Tractor_Registor updatedTractor) {
        Tractor_Registor tractor = tractorService.updateTractor(id, updatedTractor);
        return ResponseEntity.ok(tractor);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTractor(@PathVariable Long id) {
        tractorService.deleteTractor(id);
    }
}
