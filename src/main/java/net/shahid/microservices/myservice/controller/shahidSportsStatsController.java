package net.shahid.microservices.myservice.controller;

import net.shahid.microservices.myservice.domain.entity.TopScorer;
import net.shahid.microservices.myservice.domain.entity.TopScorerResponse;
import net.shahid.microservices.myservice.feign.ShahidSportsStatsFeign;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class shahidSportsStatsController {
    private final ShahidSportsStatsFeign shahidSportsStatsFeign;

    public shahidSportsStatsController(ShahidSportsStatsFeign shahidSportsStatsFeign) {
        this.shahidSportsStatsFeign = shahidSportsStatsFeign;
    }

    @GetMapping("/tournaments/topScorers")
    public List<TopScorer> getTournamentTopScorers(@RequestParam("tournament") String tournament) {
        TopScorerResponse topScorerResponse = shahidSportsStatsFeign.getTournamentTopScorers(tournament);
        List<TopScorer> topScorers = topScorerResponse.getTopScorer();
        topScorers = topScorers.stream().filter(topScorer -> topScorer.getGoal() > 10)
                .toList();
        return ResponseEntity.ok(topScorers).getBody();
    }
}
