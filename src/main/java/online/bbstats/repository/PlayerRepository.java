package online.bbstats.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import online.bbstats.repository.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	Player findByNameAndDateOfBirth(String name, LocalDate dateOfBirth);

}
