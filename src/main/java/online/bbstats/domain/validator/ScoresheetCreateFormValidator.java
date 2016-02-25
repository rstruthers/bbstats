package online.bbstats.domain.validator;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import online.bbstats.BbstatsConstants;
import online.bbstats.forms.ScoresheetCreateForm;
import online.bbstats.repository.domain.Season;
import online.bbstats.service.SeasonService;

@Component
public class ScoresheetCreateFormValidator  implements Validator {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoresheetCreateFormValidator.class);
    
    @Autowired
    private SeasonService seasonService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(ScoresheetCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        ScoresheetCreateForm form = (ScoresheetCreateForm) target;
        validateGameDate(errors, form);
        validateTeams(errors, form);
    }

    private void validateTeams(Errors errors, ScoresheetCreateForm form) {
            if (form.getHomeTeamName() == null || form.getVisitingTeamName() == null) {
                return;
            }
            if (StringUtils.equals(form.getHomeTeamName(), form.getVisitingTeamName())) {
                errors.rejectValue("homeTeamName", "names.cannot.be.equal", "Home team cannot be the same as the visiting team.");
            }
        
    }

    private void validateGameDate(Errors errors, ScoresheetCreateForm form) {
        LocalDate gameDate = null;
        try {
            gameDate = LocalDate.parse(form.getGameDate(), BbstatsConstants.DATE_TIME_FORMATTER);
        } catch (Exception ex) {
            errors.rejectValue("gameDate", "invalid.date.format", "Invalid date, format must match " + BbstatsConstants.DATE_PATTERN);
        }
        
        Season season = seasonService.findSeasonByName(form.getSeasonName());
        if (season != null && gameDate != null) {
            if (!(gameDate.isEqual(season.getStartDate()) || 
                    (gameDate.isAfter(season.getStartDate()) && 
                            (season.getEndDate() == null || gameDate.isBefore(season.getEndDate()) || gameDate.isEqual(season.getEndDate()))) )) {
                errors.rejectValue("gameDate", "game.date.not.in.range", "Invalid game date, must be within the season start and end date");
                
            }
        }
        
    }

}
