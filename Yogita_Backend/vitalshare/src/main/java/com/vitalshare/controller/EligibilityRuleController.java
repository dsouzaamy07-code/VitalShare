
package com.vitalshare.controller;

import com.vitalshare.model.EligibilityRule;
import com.vitalshare.service.EligibilityRuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eligibility-rules")
@CrossOrigin(origins = "*")
public class EligibilityRuleController {

    private final EligibilityRuleService eligibilityRuleService;

    public EligibilityRuleController(EligibilityRuleService eligibilityRuleService) {
        this.eligibilityRuleService = eligibilityRuleService;
    }

    @GetMapping
    public List<EligibilityRule> getAllRules() {
        return eligibilityRuleService.getAllRules();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EligibilityRule> getRuleById(@PathVariable Integer id) {
        return eligibilityRuleService.getRuleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{donationType}")
    public ResponseEntity<EligibilityRule> getRuleByDonationType(
            @PathVariable String donationType) {
        return eligibilityRuleService.getRuleByDonationType(donationType)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/active/{donationType}")
    public ResponseEntity<EligibilityRule> getActiveRuleByDonationType(
            @PathVariable String donationType) {
        return eligibilityRuleService.getActiveRuleByDonationType(donationType)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EligibilityRule createRule(@RequestBody EligibilityRule rule) {
        return eligibilityRuleService.saveRule(rule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EligibilityRule> updateRule(
            @PathVariable Integer id,
            @RequestBody EligibilityRule rule) {

        if (eligibilityRuleService.getRuleById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        rule.setRuleId(id);
        return ResponseEntity.ok(eligibilityRuleService.saveRule(rule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Integer id) {
        if (eligibilityRuleService.getRuleById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        eligibilityRuleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check/{donorId}/{donationType}")
    public ResponseEntity<?> checkEligibility(
        @PathVariable Integer donorId,
        @PathVariable String donationType) {

            try {
                return ResponseEntity.ok(
                    eligibilityRuleService.checkEligibility(
                        donorId, donationType
                    )
                );
            } catch (RuntimeException exception) {
                return ResponseEntity.badRequest()
                .body(exception.getMessage());
            }
        }
}
