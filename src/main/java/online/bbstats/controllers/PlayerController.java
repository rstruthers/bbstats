package online.bbstats.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.domain.validator.PlayerFormValidator;
import online.bbstats.forms.PlayerForm;
import online.bbstats.repository.domain.Team;
import online.bbstats.service.PlayerService;
import online.bbstats.service.TeamService;

@Controller
public class PlayerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerController.class);
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired TeamService teamService;
	
	@Autowired
	private PlayerFormValidator playerFormValidator;
	
	@InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(playerFormValidator);
    }
	
	@ModelAttribute("teams")
	public List<Team> getVersion() {
	   return teamService.getAllTeams();
	}
	
	

	@RequestMapping(value = "/player/create", method = RequestMethod.GET)
	public ModelAndView getPlayerCreateForm() {
		LOGGER.debug("Getting player create form");
		ModelAndView mv = new ModelAndView("player_create");
		mv.addObject("form", new PlayerForm());
		return mv;
	}

	@RequestMapping(value = "/player/create", method = RequestMethod.POST)
	public String handlePlayerCreateForm(@Valid @ModelAttribute("form") PlayerForm form, BindingResult bindingResult) {
		LOGGER.debug("Processing player create form={}, bindingResult={}", form, bindingResult);
		if (bindingResult.hasErrors()) {
            // failed validation
            return "player_create";
        }
        try {
            playerService.create(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the player", e);
            return "player_create";
        }
        // ok, redirect
        return "redirect:/players";
	}
	
	@RequestMapping(value = "/player/update/{id}", method = RequestMethod.GET)
	public ModelAndView getPlayerUpdateForm(@PathVariable("id") Long id) {
		LOGGER.debug("Getting player update form");
		ModelAndView mv = new ModelAndView("player_update");
		mv.addObject("form", new PlayerForm(playerService.getPlayerById(id)));
		return mv;
	}
	
	@RequestMapping(value = "/player/update", method = RequestMethod.POST)
	public String handlePlayerUpdateForm(@Valid @ModelAttribute("form") PlayerForm form, BindingResult bindingResult) {
		LOGGER.debug("Processing player update form={}, bindingResult={}", form, bindingResult);
		if (bindingResult.hasErrors()) {
            // failed validation
            return "player_update";
        }
        try {
            playerService.update(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the player", e);
            return "player_update";
        }
        // ok, redirect
        return "redirect:/players";
	}
	
	@RequestMapping("/players")
    public ModelAndView getTeamListPage() {
        LOGGER.debug("Getting player list page");
        return new ModelAndView("players", "players", playerService.getAllPlayers());
    }
}
