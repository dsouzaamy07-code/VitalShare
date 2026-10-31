
package com.vitalshare.controller;

import com.vitalshare.model.Donation;
import com.vitalshare.service.DonationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Integer id) {
        return donationService.getDonationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/donor/{donorId}")
    public List<Donation> getDonationsByDonorId(@PathVariable Integer donorId) {
        return donationService.getDonationsByDonorId(donorId);
    }

    @GetMapping("/type/{donationType}")
    public List<Donation> getDonationsByType(@PathVariable String donationType) {
        return donationService.getDonationsByType(donationType);
    }

    @GetMapping("/status/{status}")
    public List<Donation> getDonationsByStatus(@PathVariable String status) {
        return donationService.getDonationsByStatus(status);
    }

    @PostMapping
    public Donation createDonation(@RequestBody Donation donation) {
        return donationService.saveDonation(donation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Integer id) {
        if (donationService.getDonationById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }
}
