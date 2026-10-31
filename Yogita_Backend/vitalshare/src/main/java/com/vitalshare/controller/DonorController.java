
package com.vitalshare.controller;

import com.vitalshare.model.Donor;
import com.vitalshare.service.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "*")
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    // Get all donors
    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    // Get donor by ID
    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Integer id) {
        return donorService.getDonorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get donor by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Donor> getDonorByUserId(@PathVariable Integer userId) {
        return donorService.getDonorByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Search donors by city
    @GetMapping("/city/{city}")
    public List<Donor> getDonorsByCity(@PathVariable String city) {
        return donorService.getAvailableDonorsByCity(city);
    }

    // Search donors by pincode
    @GetMapping("/pincode/{pincode}")
    public List<Donor> getDonorsByPincode(@PathVariable String pincode) {
        return donorService.getAvailableDonorsByPincode(pincode);
    }

    // Search donors by blood group
    @GetMapping("/blood-group/{bloodGroupId}")
    public List<Donor> getDonorsByBloodGroup(
            @PathVariable Integer bloodGroupId) {
        return donorService.getAvailableDonorsByBloodGroup(bloodGroupId);
    }

    // Add or update donor
    @PostMapping
    public Donor saveDonor(@RequestBody Donor donor) {
        return donorService.saveDonor(donor);
    }

    // Delete donor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Integer id) {
        donorService.deleteDonor(id);
        return ResponseEntity.noContent().build();
    }
}
