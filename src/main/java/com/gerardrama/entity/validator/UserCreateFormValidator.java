package com.gerardrama.entity.validator;

import com.gerardrama.entity.form.UserCreateForm;
import com.gerardrama.repository.UserRepository;
import com.gerardrama.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserCreateFormValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(UserCreateFormValidator.class);

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserCreateFormValidator(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.info("Validating {}", target);
        UserCreateForm form = (UserCreateForm) target;
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    public boolean isValidPassword(String password,String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

//    Validation must include
//    1. At least one upper and one lowercase letter
//    2. At least one digit
//    3. At least 8 characters long
    private void validatePasswords(Errors errors, UserCreateForm form) {
        String upperLowerRegex = "^(?=[^a-z]*[a-z])";
        String oneDigitRegex = "^(?=.*?[0-9])";
        String lengthRegex = "^.{8,}$";

        if (!form.getPassword().equals(form.getPasswordRepeated())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
        else {
            if(!isValidPassword(form.getPassword(), lengthRegex)){
                errors.reject("password.incorrect", "Password must be at least 8 characters long.");
            }
            if(!isValidPassword(form.getPassword(), upperLowerRegex)){
                errors.reject("password.incorrect", "Password must contain at least one upper and lower case letter.");
            }
            if(!isValidPassword(form.getPassword(), oneDigitRegex)){
                errors.reject("password.incorrect", "Password must contain at least one digit.");
            }
        }
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
        if (userService.getUserByEmail(form.getEmail()).isPresent()) {
            errors.reject("email.exists", "User with this email already exists");
        }
    }
}
