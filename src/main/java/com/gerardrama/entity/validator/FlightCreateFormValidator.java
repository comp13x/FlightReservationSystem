package com.gerardrama.entity.validator;

import com.gerardrama.controller.UsersController;
import com.gerardrama.entity.form.FlightCreateForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;

@Component
public class FlightCreateFormValidator implements Validator {
    private static final Logger LOGGER = LogManager.getLogger(FlightCreateFormValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(FlightCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LOGGER.info("Validating {}", target);
        FlightCreateForm form = (FlightCreateForm) target;
        validateDate(errors, form);
    }

    public void validateDate(Errors errors, FlightCreateForm flightCreateForm){
        if(flightCreateForm.getArrivalDate().isBefore(flightCreateForm.getDepartureDate())){
            errors.reject("date.incorrect", "Arrival date cannot be before departure");
        }
        if(flightCreateForm.getDepartureDate().isBefore(LocalDate.now())){
            errors.reject("date.incorrect", "Departure date cannot be before today");
        }
    }
}
