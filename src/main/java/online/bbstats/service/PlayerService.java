package online.bbstats.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.forms.PlayerForm;
import online.bbstats.repository.PlayerRepository;
import online.bbstats.repository.domain.Player;

@Service
public class PlayerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);
	
	@Autowired
	PlayerRepository playerRepository;
	
	public Player create(PlayerForm playerForm) {
		Player player = new Player();
		BeanUtils.copyProperties(playerForm, player);
		playerRepository.save(player);
		return player;
	}

	public List<Player> getAllPlayers() {
		LOGGER.debug("Getting all players");
		return playerRepository.findAll();
	}
	
	public Player getPlayerById(Long id) {
		LOGGER.debug("Getting player by id: " + id);
		return playerRepository.findOne(id);
	}

	public void update(PlayerForm playerForm) {
		Player player = new Player();
		BeanUtils.copyProperties(playerForm, player);
		playerRepository.save(player);
	}
}
