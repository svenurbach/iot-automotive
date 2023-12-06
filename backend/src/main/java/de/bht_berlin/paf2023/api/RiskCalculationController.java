package de.bht_berlin.paf2023.api;

import de.bht_berlin.paf2023.service.RiskCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/risk")
public class RiskCalculationController {

    // User Story: Risikobereitschaft berechnen
    // https://trello.com/c/qWvSlzYY/25-user-story-risikobereitschaft-berechnen

    // Wir brauchen: Versicherungsunternehmen, Fahrverhalten, Fahrer

    @Autowired
    private RiskCalculationService riskCalculationService;

    // Risikobereitschaft eines Autofahrers ausgeben
    // /{user}/risk

    // Risikobereitschaft für einen bestimmten Zeitraum ausgeben
    // /{user}/risk/{minDate}/{maxDate}

}
