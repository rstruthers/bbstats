package online.bbstats.domain.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import online.bbstats.forms.TeamForm;

@Component
public class TeamFormValidator implements Validator {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamFormValidator.class);
	
	@Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TeamForm.class);
    }

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.debug("Validating {}", target);
	}
}
