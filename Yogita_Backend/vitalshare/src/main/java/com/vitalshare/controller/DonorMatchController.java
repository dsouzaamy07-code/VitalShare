
package com.vitalshare.controller;

import com.vitalshare.model.DonorMatch;
import com.vitalshare.service.DonorMatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donor-matches")
@CrossOrigin(origins = "*")
public class DonorMatchController {

    private final DonorMatchService donorMatchService;

    public DonorMatchController(DonorMatchService donorMatchService) {
        this.donorMatchService = donorMatchService;
    }

    @GetMapping
    public List<DonorMatch> getAllMatches() {
        return donorMatchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorMatch> getMatchById(@PathVariable Integer id) {
        return donorMatchService.getMatchById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/request/{requestId}")
    public List<DonorMatch> getMatchesByRequestId(
            @PathVariable Integer requestId) {
        return donorMatchService.getMatchesByRequestId(requestId);
    }

    @GetMapping("/donor/{donorId}")
    public List<DonorMatch> getMatchesByDonorId(
            @PathVariable Integer donorId) {
        return donorMatchService.getMatchesByDonorId(donorId);
    }

    @GetMapping("/status/{status}")
    public List<DonorMatch> getMatchesByStatus(
            @PathVariable String status) {
        return donorMatchService.getMatchesByStatus(status);
    }

    @GetMapping("/exists")
    public boolean matchExists(
            @RequestParam Integer requestId,
            @RequestParam Integer donorId) {
        return donorMatchService.matchExists(requestId, donorId);
    }

    @PostMapping("/generate/{requestId}")
    public ResponseEntity<?> generateMatches(
            @PathVariable Integer requestId) {
        try {
            List<DonorMatch> matches =
                    donorMatchService.generateMatches(requestId);

            return ResponseEntity.ok(matches);

        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }

    @PostMapping
    public DonorMatch createMatch(@RequestBody DonorMatch donorMatch) {
        return donorMatchService.saveMatch(donorMatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer id) {
        if (donorMatchService.getMatchById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        donorMatchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
