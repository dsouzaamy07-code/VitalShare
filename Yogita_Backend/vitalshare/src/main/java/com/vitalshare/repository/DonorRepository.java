package com.vitalshare.repository;

import com.vitalshare.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Integer> {

    Optional<Donor> findByUserUserId(Integer userId);

    List<Donor> findByCityIgnoreCaseAndIsAvailableTrue(String city);

    List<Donor> findByPincodeAndIsAvailableTrue(String pincode);

    List<Donor> findByBloodGroupBloodGroupIdAndIsAvailableTrue(Integer bloodGroupId);

    List<Donor> findByIsAvailableTrueAndConsentToContactTrue();

}