
package com.vitalshare.service;

import com.vitalshare.model.BloodRequest;
import com.vitalshare.model.Donor;
import com.vitalshare.model.DonorMatch;
import com.vitalshare.repository.BloodRequestRepository;
import com.vitalshare.repository.DonorMatchRepository;
import com.vitalshare.repository.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class DonorMatchService {

    private final DonorMatchRepository donorMatchRepository;
    private final BloodRequestRepository bloodRequestRepository;
    private final DonorRepository donorRepository;

    public DonorMatchService(
            DonorMatchRepository donorMatchRepository,
            BloodRequestRepository bloodRequestRepository,
            DonorRepository donorRepository) {

        this.donorMatchRepository = donorMatchRepository;
        this.bloodRequestRepository = bloodRequestRepository;
        this.donorRepository = donorRepository;
    }

    public List<DonorMatch> getAllMatches() {
        return donorMatchRepository.findAll();
    }

    public Optional<DonorMatch> getMatchById(Integer id) {
        return donorMatchRepository.findById(id);
    }

    public List<DonorMatch> getMatchesByRequestId(Integer requestId) {
        return donorMatchRepository.findByBloodRequestRequestId(requestId);
    }

    public List<DonorMatch> getMatchesByDonorId(Integer donorId) {
        return donorMatchRepository.findByDonorDonorId(donorId);
    }

    public List<DonorMatch> getMatchesByStatus(String status) {
        return donorMatchRepository.findByMatchStatus(status);
    }

    public boolean matchExists(Integer requestId, Integer donorId) {
        return donorMatchRepository
                .existsByBloodRequestRequestIdAndDonorDonorId(requestId, donorId);
    }

    @Transactional
    public List<DonorMatch> generateMatches(Integer requestId) {

        BloodRequest request = bloodRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new RuntimeException("Blood request not found"));

        if (!"OPEN".equalsIgnoreCase(request.getStatus())) {
            throw new RuntimeException("Blood request is not open");
        }

        List<Donor> availableDonors =
                donorRepository.findByBloodGroupBloodGroupIdAndIsAvailableTrue(
                        request.getBloodGroup().getBloodGroupId());

        List<Donor> eligibleDonors = new ArrayList<>();

        for (Donor donor : availableDonors) {

            if (!Boolean.TRUE.equals(donor.getConsentToContact())) {
                continue;
            }

            if (matchExists(requestId, donor.getDonorId())) {
                continue;
            }

            eligibleDonors.add(donor);
        }

        // Prioritize donors from the same city.
        eligibleDonors.sort(
                Comparator.comparing(
                        donor -> !donor.getCity().equalsIgnoreCase(request.getCity())
                )
        );

        List<DonorMatch> newMatches = new ArrayList<>();

        for (Donor donor : eligibleDonors) {

            DonorMatch match = new DonorMatch();

            match.setBloodRequest(request);
            match.setDonor(donor);
            match.setMatchStatus("SUGGESTED");

            newMatches.add(match);
        }

        return donorMatchRepository.saveAll(newMatches);
    }

    public DonorMatch saveMatch(DonorMatch donorMatch) {
        return donorMatchRepository.save(donorMatch);
    }

    public void deleteMatch(Integer id) {
        donorMatchRepository.deleteById(id);
    }
}
