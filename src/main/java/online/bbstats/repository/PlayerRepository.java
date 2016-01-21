package online.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.bbstats.repository.domain.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
