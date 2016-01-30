package online.bbstats.domain.validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import online.bbstats.domain.UserForm;

@Component
public class UserUpdateFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserUpdateFormValidator.class);

    public UserUpdateFormValidator() {
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserForm form = (UserForm) target;
        validatePasswords(errors, form);
    }

    private void validatePasswords(Errors errors, UserForm form) {
    	if ((StringUtils.trimToNull(form.getPassword()) == null) &&
    		(StringUtils.trimToNull(form.getPasswordRepeated()) == null )) {
    		return;
    	}
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.rejectValue("password", "password.no_match", "Passwords do not match");
        }
    }
}
