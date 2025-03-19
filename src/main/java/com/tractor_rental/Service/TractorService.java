package com.tractor_rental.Service;

import com.tractor_rental.modal.Tractor_Registor;
import com.tractor_rental.repositories.TractorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteTractor(Long id) {
        tractorRepository.deleteById(id);
    }
}
