package com.vitalshare.repository;

import com.vitalshare.model.DonorMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonorMatchRepository extends JpaRepository<DonorMatch, Integer> {

    List<DonorMatch> findByBloodRequestRequestId(Integer requestId);

    List<DonorMatch> findByDonorDonorId(Integer donorId);

    List<DonorMatch> findByMatchStatus(String matchStatus);

    boolean existsByBloodRequestRequestIdAndDonorDonorId(
            Integer requestId, Integer donorId);

}