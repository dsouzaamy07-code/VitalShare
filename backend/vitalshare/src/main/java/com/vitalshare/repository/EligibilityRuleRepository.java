package com.vitalshare.repository;

import com.vitalshare.model.EligibilityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EligibilityRuleRepository extends JpaRepository<EligibilityRule, Integer> {

    Optional<EligibilityRule> findByDonationType(String donationType);

    Optional<EligibilityRule> findByDonationTypeAndIsActiveTrue(String donationType);

}