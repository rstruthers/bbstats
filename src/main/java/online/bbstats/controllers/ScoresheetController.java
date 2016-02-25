package online.bbstats.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.repository.domain.Scoresheet;
import online.bbstats.service.ScoresheetService;

@Controller
public class ScoresheetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoresheetController.class);
    
    @Autowired
    private ScoresheetService scoresheetService;
    
    @RequestMapping(value = "/scoresheet/{id}", method = RequestMethod.GET)
    public ModelAndView getScoresheet(@PathVariable("id") Long id) {
        LOGGER.debug("Get scoresheet");
        ModelAndView mav = new ModelAndView("scoresheet");
        Scoresheet scoresheet = scoresheetService.findById(id);
        mav.addObject("scoresheet", scoresheet);
        return mav;
    }
}
