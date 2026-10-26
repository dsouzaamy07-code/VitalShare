package com.vitalshare.repository;

import com.vitalshare.model.Donation;
import com.vitalshare.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Integer> {

    List<Donation> findByDonor(Donor donor);

    List<Donation> findByDonorDonorId(Integer donorId);

    List<Donation> findByDonationType(String donationType);

    List<Donation> findByStatus(String status);

}