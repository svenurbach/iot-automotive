package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.service.RiskCalculationService;
import de.bht_berlin.paf2023.service.SafetyRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/safety")
public class SafetyCalculationController {

    // User Story: Risikobereitschaft berechnen
    // https://trello.com/c/qWvSlzYY/25-user-story-risikobereitschaft-berechnen

    // Wir brauchen: Versicherungsunternehmen, Fahrverhalten, Fahrer

    @Autowired
    private SafetyRecommendationService safetyRecommendationService;

    // Get calculated Safety score
    // /{user}/safetyscore

    // Get safety recommendations
    // /{user}/safetyrecommendations

    // Get safety score over time
    // /{user}/safetyscore?from=&to=

}
