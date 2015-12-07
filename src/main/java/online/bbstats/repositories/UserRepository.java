package online.bbstats.repositories;

import online.bbstats.domain.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

	Optional<User> findOneByUsername(String username);
}