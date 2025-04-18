package com.tractor_rental.Service;

import com.tractor_rental.modal.Tractor_Registor;
import com.tractor_rental.repositories.TractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TractorService {
    @Autowired
    private TractorRepository tractorRepository;

    public List<Tractor_Registor> getAllTractors() {
        return tractorRepository.findAll();
    }

    public Tractor_Registor getTractorById(Long id) {
        return tractorRepository.findById(id).orElse(null);
    }

    public Tractor_Registor saveTractor(Tractor_Registor tractor) {
        return tractorRepository.save(tractor);
    }

    public Tractor_Registor updateTractor(Long id, Tractor_Registor updatedTractor) {
        Optional<Tractor_Registor> existingTractorOpt = tractorRepository.findById(id);

        if (existingTractorOpt.isPresent()) {
            Tractor_Registor existingTractor = existingTractorOpt.get();
            existingTractor.setDriverName(updatedTractor.getDriverName());
            existingTractor.setPhoneNumber(updatedTractor.getPhoneNumber());
            existingTractor.setLocation(updatedTractor.getLocation());
            existingTractor.setRentalPrice(updatedTractor.getRentalPrice());

            return tractorRepository.save(existingTractor);
        } else {
            throw new RuntimeException("Tractor with ID " + id + " not found.");
        }
    }
    public void deleteTractor(Long id) {
        tractorRepository.deleteById(id);
    }
}
