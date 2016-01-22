package online.bbstats.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.domain.validator.TeamFormValidator;
import online.bbstats.forms.TeamForm;
import online.bbstats.service.TeamService;

@Controller
public class TeamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private TeamFormValidator teamFormValidator;
	
	@InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(teamFormValidator);
    }

	@RequestMapping(value = "/team/create", method = RequestMethod.GET)
	public ModelAndView getTeamCreateForm() {
		LOGGER.debug("Getting team create form");
		return new ModelAndView("team_create", "form", new TeamForm());
	}

	@RequestMapping(value = "/team/create", method = RequestMethod.POST)
	public String handleTeamCreateForm(@Valid @ModelAttribute("form") TeamForm form, BindingResult bindingResult) {
		LOGGER.debug("Processing team create form={}, bindingResult={}", form, bindingResult);
		if (bindingResult.hasErrors()) {
            // failed validation
            return "team_create";
        }
        try {
            teamService.create(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the team", e);
            return "team_create";
        }
        // ok, redirect
        return "redirect:/teams";
	}
	
	@RequestMapping("/teams")
    public ModelAndView getTeamListPage() {
        LOGGER.debug("Getting team list page");
        return new ModelAndView("teams", "teams", teamService.getAllTeams());
    }
}