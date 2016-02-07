package online.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.bbstats.repository.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	Team findByName(String name);
}
