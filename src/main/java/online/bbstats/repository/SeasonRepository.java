package online.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.bbstats.repository.domain.Season;

public interface SeasonRepository extends JpaRepository<Season, Long> {
	Season findByName(String name);
}
