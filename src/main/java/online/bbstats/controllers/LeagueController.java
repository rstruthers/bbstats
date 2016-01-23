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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.domain.validator.LeagueFormValidator;
import online.bbstats.forms.LeagueForm;
import online.bbstats.service.LeagueService;

@Controller
public class LeagueController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LeagueController.class);
	
	@Autowired
	private LeagueService leagueService;
	
	@Autowired
	private LeagueFormValidator leagueFormValidator;
	
	@InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(leagueFormValidator);
    }

	@RequestMapping(value = "/league/create", method = RequestMethod.GET)
	public ModelAndView getLeagueCreateForm() {
		LOGGER.debug("Getting league create form");
		return new ModelAndView("league_create", "form", new LeagueForm());
	}

	@RequestMapping(value = "/league/create", method = RequestMethod.POST)
	public String handleLeagueCreateForm(@Valid @ModelAttribute("form") LeagueForm form, BindingResult bindingResult) {
		LOGGER.debug("Processing league create form={}, bindingResult={}", form, bindingResult);
		if (bindingResult.hasErrors()) {
            // failed validation
            return "league_create";
        }
        try {
            leagueService.create(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the league", e);
            return "league_create";
        }
        // ok, redirect
        return "redirect:/leagues";
	}
	
	@RequestMapping(value = "/league/update/{id}", method = RequestMethod.GET)
	public ModelAndView getLeagueUpdateForm(@PathVariable("id") Long id) {
		LOGGER.debug("Getting league update form");
		ModelAndView mv = new ModelAndView("league_update");
		mv.addObject("form", new LeagueForm(leagueService.getLeagueById(id)));
		return mv;
	}
	
	@RequestMapping(value = "/league/update", method = RequestMethod.POST)
	public String handleLeagueUpdateForm(@Valid @ModelAttribute("form") LeagueForm form, BindingResult bindingResult) {
		LOGGER.debug("Processing league update form={}, bindingResult={}", form, bindingResult);
		if (bindingResult.hasErrors()) {
            // failed validation
            return "league_update";
        }
        try {
            leagueService.update(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the league", e);
            return "league_update";
        }
        // ok, redirect
        return "redirect:/leagues";
	}
	
	
	@RequestMapping("/leagues")
    public ModelAndView getLeagueListPage() {
        LOGGER.debug("Getting league list page");
        return new ModelAndView("leagues", "leagues", leagueService.getAllLeagues());
    }
}
