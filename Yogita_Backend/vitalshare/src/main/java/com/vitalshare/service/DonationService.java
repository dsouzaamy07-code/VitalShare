
package com.vitalshare.service;

import com.vitalshare.model.Donation;
import com.vitalshare.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Optional<Donation> getDonationById(Integer id) {
        return donationRepository.findById(id);
    }

    public List<Donation> getDonationsByDonorId(Integer donorId) {
        return donationRepository.findByDonorDonorId(donorId);
    }

    public List<Donation> getDonationsByType(String donationType) {
        return donationRepository.findByDonationType(donationType);
    }

    public List<Donation> getDonationsByStatus(String status) {
        return donationRepository.findByStatus(status);
    }

    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public void deleteDonation(Integer id) {
        donationRepository.deleteById(id);
    }
}
