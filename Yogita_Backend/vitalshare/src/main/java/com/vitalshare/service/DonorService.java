
package com.vitalshare.service;

import com.vitalshare.model.Donor;
import com.vitalshare.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonorService {

    private final DonorRepository donorRepository;

    public DonorService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Optional<Donor> getDonorById(Integer id) {
        return donorRepository.findById(id);
    }

    public Optional<Donor> getDonorByUserId(Integer userId) {
        return donorRepository.findByUserUserId(userId);
    }

    public List<Donor> getAvailableDonorsByCity(String city) {
        return donorRepository.findByCityIgnoreCaseAndIsAvailableTrue(city);
    }

    public List<Donor> getAvailableDonorsByPincode(String pincode) {
        return donorRepository.findByPincodeAndIsAvailableTrue(pincode);
    }

    public List<Donor> getAvailableDonorsByBloodGroup(Integer bloodGroupId) {
        return donorRepository.findByBloodGroupBloodGroupIdAndIsAvailableTrue(bloodGroupId);
    }

    public Donor saveDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public void deleteDonor(Integer id) {
        donorRepository.deleteById(id);
    }
}
