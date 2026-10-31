
        package com.vitalshare.repository;

import com.vitalshare.model.BloodRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Integer> {

    List<BloodRequest> findByStatus(String status);

    List<BloodRequest> findByUrgency(String urgency);

    List<BloodRequest> findByCityIgnoreCase(String city);

    List<BloodRequest> findByPincode(String pincode);

    List<BloodRequest> findByRequesterUserId(Integer userId);

}