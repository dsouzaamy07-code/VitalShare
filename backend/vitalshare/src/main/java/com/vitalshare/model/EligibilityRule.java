
package com.vitalshare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "eligibility_rules")
public class EligibilityRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private Integer ruleId;

    @Column(name = "donation_type", nullable = false,
            unique = true, length = 20)
    private String donationType;

    @Column(name = "minimum_interval_days", nullable = false)
    private Integer minimumIntervalDays;

    @Column(name = "is_active")
    private Boolean isActive = true;

    public EligibilityRule() {
    }

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public Integer getMinimumIntervalDays() {
        return minimumIntervalDays;
    }

    public void setMinimumIntervalDays(Integer minimumIntervalDays) {
        this.minimumIntervalDays = minimumIntervalDays;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
