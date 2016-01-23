package online.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.bbstats.repository.domain.League;

public interface LeagueRepository extends JpaRepository<League, Long> {

}
