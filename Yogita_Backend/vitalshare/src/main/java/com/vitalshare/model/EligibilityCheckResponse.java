
package com.vitalshare.model;

import java.time.LocalDate;

public class EligibilityCheckResponse {

    private Integer donorId;
    private String donationType;
    private boolean eligible;
    private LocalDate lastDonationDate;
    private LocalDate eligibleAgainFrom;
    private Integer daysRemaining;
    private String message;

    public EligibilityCheckResponse() {
    }

    public Integer getDonorId() {
        return donorId;
    }

    public void setDonorId(Integer donorId) {
        this.donorId = donorId;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public boolean isEligible() {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

    public LocalDate getLastDonationDate() {
        return lastDonationDate;
    }

    public void setLastDonationDate(LocalDate lastDonationDate) {
        this.lastDonationDate = lastDonationDate;
    }

    public LocalDate getEligibleAgainFrom() {
        return eligibleAgainFrom;
    }

    public void setEligibleAgainFrom(LocalDate eligibleAgainFrom) {
        this.eligibleAgainFrom = eligibleAgainFrom;
    }

    public Integer getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(Integer daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
