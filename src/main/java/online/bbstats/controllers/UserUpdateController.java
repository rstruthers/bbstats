package online.bbstats.controllers;

import java.util.Optional;

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

import online.bbstats.domain.User;
import online.bbstats.domain.UserForm;
import online.bbstats.domain.validator.UserUpdateFormValidator;
import online.bbstats.service.user.UserService;

@Controller
public class UserUpdateController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdateController.class);
	
	private final UserService userService;
	private final UserUpdateFormValidator userUpdateFormValidator;
	
	@Autowired
	public UserUpdateController(UserService userService, UserUpdateFormValidator userUpdateFormValidator) {
		this.userService = userService;
		this.userUpdateFormValidator = userUpdateFormValidator;
	}
	
	@InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userUpdateFormValidator);
    }
	
    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.GET)
	public ModelAndView getUserUpdateForm(@PathVariable("id") Long id) {
		LOGGER.debug("Getting user update form");
		ModelAndView mv = new ModelAndView("user_update");
		Optional<User> user = userService.getUserById(id);
		UserForm userForm = new UserForm(user.get());
		mv.addObject("form", userForm);
		return mv;
	}
    
	@RequestMapping(value = "/user/update", method = RequestMethod.POST)
	public String handleTeamUpdateForm(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult) {
		LOGGER.debug("Processing user update form={}, bindingResult={}", form, bindingResult);
		if (bindingResult.hasErrors()) {
            // failed validation
            return "user_update";
        }
        try {
            userService.update(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("Exception occurred when trying to save the user", e);
            return "user_update";
        }
        // ok, redirect
        return "redirect:/users";
	}
	
}
