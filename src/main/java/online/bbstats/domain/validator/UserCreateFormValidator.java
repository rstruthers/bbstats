package online.bbstats.domain.validator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import online.bbstats.domain.UserForm;
import online.bbstats.service.user.UserService;

@Component
public class UserCreateFormValidator implements Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreateFormValidator.class);
    private final UserService userService;

    @Autowired
    public UserCreateFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.debug("Validating {}", target);
        UserForm form = (UserForm) target;
        if (StringUtils.trimToNull(form.getPassword()) == null) {
        	errors.rejectValue("password", "password.empty", "Password may not be empty");
        }
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validatePasswords(Errors errors, UserForm form) {
        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.rejectValue("password",  "passwords.must.match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserForm form) {
    	if (StringUtils.trimToNull(form.getEmail()) == null) {
    		errors.rejectValue("email", "email.empty", "Email may not be empty");
    	}
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.rejectValue("email", "user.exists", "User with this email already exists");
        }
    }
}
