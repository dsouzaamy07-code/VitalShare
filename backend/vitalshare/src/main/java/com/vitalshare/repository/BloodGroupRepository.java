package com.vitalshare.repository;

import com.vitalshare.model.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BloodGroupRepository extends JpaRepository<BloodGroup, Integer> {

    Optional<BloodGroup> findByBloodGroupName(String bloodGroupName);

    boolean existsByBloodGroupName(String bloodGroupName);

}