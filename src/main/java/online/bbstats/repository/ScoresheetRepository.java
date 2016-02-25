package online.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.bbstats.repository.domain.Scoresheet;

public interface ScoresheetRepository extends JpaRepository<Scoresheet, Long> {

}
