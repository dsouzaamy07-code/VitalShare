
package com.vitalshare.service;

import com.vitalshare.model.Donation;
import com.vitalshare.model.EligibilityCheckResponse;
import com.vitalshare.model.EligibilityRule;
import com.vitalshare.repository.DonationRepository;
import com.vitalshare.repository.EligibilityRuleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class EligibilityRuleService {

    private final EligibilityRuleRepository eligibilityRuleRepository;
    private final DonationRepository donationRepository;

    public EligibilityRuleService(
            EligibilityRuleRepository eligibilityRuleRepository,
            DonationRepository donationRepository) {
        this.eligibilityRuleRepository = eligibilityRuleRepository;
        this.donationRepository = donationRepository;
    }

    public List<EligibilityRule> getAllRules() {
        return eligibilityRuleRepository.findAll();
    }

    public Optional<EligibilityRule> getRuleById(Integer id) {
        return eligibilityRuleRepository.findById(id);
    }

    public Optional<EligibilityRule> getRuleByDonationType(String donationType) {
        return eligibilityRuleRepository.findByDonationType(donationType);
    }

    public Optional<EligibilityRule> getActiveRuleByDonationType(String donationType) {
        return eligibilityRuleRepository.findByDonationTypeAndIsActiveTrue(donationType);
    }

    public EligibilityRule saveRule(EligibilityRule rule) {
        return eligibilityRuleRepository.save(rule);
    }

    public void deleteRule(Integer id) {
        eligibilityRuleRepository.deleteById(id);
    }

    public EligibilityCheckResponse checkEligibility(
            Integer donorId, String donationType) {

        EligibilityRule rule = eligibilityRuleRepository
                .findByDonationTypeAndIsActiveTrue(donationType)
                .orElseThrow(() ->
                        new RuntimeException("No active eligibility rule found"));

        List<Donation> donations =
                donationRepository.findByDonorDonorIdAndStatusIgnoreCase(
                        donorId,
                        "COMPLETED",
                        Sort.by(Sort.Direction.DESC, "donationDate")
                );

        LocalDate today = LocalDate.now();

        EligibilityCheckResponse response = new EligibilityCheckResponse();
        response.setDonorId(donorId);
        response.setDonationType(donationType);

        if (donations.isEmpty()) {
            response.setEligible(true);
            response.setDaysRemaining(0);
            response.setMessage("Eligible based on recorded donation history.");
            return response;
        }

        LocalDate lastDonationDate = donations.get(0).getDonationDate();

        response.setLastDonationDate(lastDonationDate);

        LocalDate eligibleAgainFrom = lastDonationDate.plusDays(
                rule.getMinimumIntervalDays()
        );

        response.setEligibleAgainFrom(eligibleAgainFrom);

        if (!today.isBefore(eligibleAgainFrom)) {
            response.setEligible(true);
            response.setDaysRemaining(0);
            response.setMessage("Minimum waiting period completed.");
        } else {
            int daysRemaining = (int) ChronoUnit.DAYS.between(
                    today, eligibleAgainFrom
            );

            response.setEligible(false);
            response.setDaysRemaining(daysRemaining);
            response.setMessage(
                    "Donor must wait " + daysRemaining + " more days."
            );
        }

        return response;
    }
}
