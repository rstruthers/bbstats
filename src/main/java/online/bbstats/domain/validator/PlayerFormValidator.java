package online.bbstats.domain.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import online.bbstats.forms.PlayerForm;

@Component
public class PlayerFormValidator implements Validator {
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerFormValidator.class);
	
	@Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(PlayerForm.class);
    }

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.debug("Validating {}", target);
	}
}
