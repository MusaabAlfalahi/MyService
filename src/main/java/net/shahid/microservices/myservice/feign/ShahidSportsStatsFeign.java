package net.shahid.microservices.myservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.shahid.microservices.myservice.domain.entity.TopScorerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "shahid-sports-stats", url = "localhost:8080")
public interface ShahidSportsStatsFeign {
    @GetMapping("/shahid-sports-stats/soccerData/tournaments/topScorers")
    @CircuitBreaker(name = "TopScorers")
    TopScorerResponse getTournamentTopScorers(@RequestParam("tournament") String tournament);
}
