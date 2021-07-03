package com.gerardrama.entity.validator;

import com.gerardrama.controller.UsersController;
import com.gerardrama.entity.form.TripCreateForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;

@Component
public class TripCreateFormValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(TripCreateFormValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TripCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.info("Validating {}", target);
        TripCreateForm form = (TripCreateForm) target;
        validateDate(errors, form);
    }

    public void validateDate(Errors errors, TripCreateForm tripCreateForm){
        if(tripCreateForm.getArrivalDate().isBefore(tripCreateForm.getDepartureDate())){
            errors.reject("date.incorrect", "Arrival date cannot be before departure");
        }
        if(tripCreateForm.getDepartureDate().isBefore(LocalDate.now())){
            errors.reject("date.incorrect", "Departure date cannot be before today");
        }
    }
}
