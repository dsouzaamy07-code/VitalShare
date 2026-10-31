
package com.vitalshare.controller;

import com.vitalshare.model.BloodRequest;
import com.vitalshare.service.BloodRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blood-requests")
@CrossOrigin(origins = "*")
public class BloodRequestController {

    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    @GetMapping
    public List<BloodRequest> getAllRequests() {
        return bloodRequestService.getAllRequests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodRequest> getRequestById(
            @PathVariable Integer id) {
        return bloodRequestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/open")
    public List<BloodRequest> getOpenRequests() {
        return bloodRequestService.getOpenRequests();
    }

    @PostMapping
    public BloodRequest createRequest(@RequestBody BloodRequest request) {
        return bloodRequestService.createRequest(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodRequest> updateRequest(
            @PathVariable Integer id,
            @RequestBody BloodRequest request) {

        if (bloodRequestService.getRequestById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        request.setRequestId(id);
        return ResponseEntity.ok(
                bloodRequestService.updateRequest(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Integer id) {
        if (bloodRequestService.getRequestById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        bloodRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}
