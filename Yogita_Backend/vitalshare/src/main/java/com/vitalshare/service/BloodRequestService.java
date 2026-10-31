
package com.vitalshare.service;

import com.vitalshare.model.BloodRequest;
import com.vitalshare.repository.BloodRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BloodRequestService {

    private final BloodRequestRepository bloodRequestRepository;

    public BloodRequestService(BloodRequestRepository bloodRequestRepository) {
        this.bloodRequestRepository = bloodRequestRepository;
    }

    public List<BloodRequest> getAllRequests() {
        return bloodRequestRepository.findAll();
    }

    public Optional<BloodRequest> getRequestById(Integer id) {
        return bloodRequestRepository.findById(id);
    }

    public List<BloodRequest> getOpenRequests() {
        return bloodRequestRepository.findByStatus("OPEN");
    }

    public BloodRequest createRequest(BloodRequest request) {
        request.setStatus("OPEN");
        return bloodRequestRepository.save(request);
    }

    public BloodRequest updateRequest(BloodRequest request) {
        return bloodRequestRepository.save(request);
    }

    public void deleteRequest(Integer id) {
        bloodRequestRepository.deleteById(id);
    }
}
