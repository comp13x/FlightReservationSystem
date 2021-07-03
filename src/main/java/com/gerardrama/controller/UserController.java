package com.gerardrama.controller;

import com.gerardrama.entity.form.FlightCreateForm;
import com.gerardrama.entity.form.TripCreateForm;
import com.gerardrama.entity.form.UserCreateForm;
import com.gerardrama.entity.validator.FlightCreateFormValidator;
import com.gerardrama.entity.validator.TripCreateFormValidator;
import com.gerardrama.entity.validator.UserCreateFormValidator;
import com.gerardrama.service.user.impl.FlightServiceImpl;
import com.gerardrama.service.user.impl.TripServiceImpl;
import com.gerardrama.service.user.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import org.apache.logging.log4j.Logger;

@Controller
@RequestMapping("user")
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserServiceImpl userService;
    private final TripServiceImpl tripService;
    private final FlightServiceImpl flightService;

    private final UserCreateFormValidator userCreateFormValidator;
    private final TripCreateFormValidator tripCreateFormValidator;
    private final FlightCreateFormValidator flightCreateFormValidator;

    @Autowired
    public UserController(UserServiceImpl userService, TripServiceImpl tripService, UserCreateFormValidator userCreateFormValidator, TripCreateFormValidator tripCreateFormValidator, FlightServiceImpl flightService, FlightCreateFormValidator flightCreateFormValidator) {
        this.userService = userService;
        this.tripService = tripService;
        this.flightService = flightService;

        this.userCreateFormValidator = userCreateFormValidator;
        this.tripCreateFormValidator = tripCreateFormValidator;
        this.flightCreateFormValidator = flightCreateFormValidator;
    }

    /*
        Validation Binders
    */
    @InitBinder(value = "userForm")
    public void initBinderUser(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    @InitBinder(value = "tripForm")
    public void initBinderTrip(WebDataBinder binder) {
        binder.addValidators(tripCreateFormValidator);
    }

    @InitBinder(value = "flightForm")
    public void initBinderFlight(WebDataBinder binder) {
        binder.addValidators(flightCreateFormValidator);
    }

    @InitBinder(value = "updateTrip")
    public void initBinderTripUpdate(WebDataBinder binder) {
        binder.addValidators(tripCreateFormValidator);
    }

    /*
        Get single user
    */
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        LOGGER.info("Getting user page for user={}", id);
        return new ModelAndView("users/user", "user", userService.getUserById(id));
    }

    /*
        Get trips
    */
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping("/{id}/trips")
    public String getUsersPage(@PathVariable long id, Model model) {
        LOGGER.info("Getting trips page");
        model.addAttribute("trips", tripService.getAllTrips(id));
        model.addAttribute("user", userService.getUserById(id));
        return "trips/trips";
    }

    /*
        Get flights
    */
    @PreAuthorize("@currentUserServiceImpl.canAccessFlights(principal, #id, #tripId)")
    @GetMapping("/{id}/trips/{tripId}/flights")
    public String getFlightsPage(@PathVariable long id,
                                 @PathVariable Integer tripId, Model model) {
        LOGGER.info("Getting flights page");
        model.addAttribute("flights", flightService.getAllFlights(tripId));
        return "flights/flights";
    }

    /*
        Creating users
    */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/create")
    public ModelAndView getUserCreatePage() {
        LOGGER.info("Getting user create form");
        return new ModelAndView("users/user_create", "userForm", new UserCreateForm());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/create")
    public String handleUserCreateForm(@Valid @ModelAttribute("userForm") UserCreateForm userCreateForm, BindingResult bindingResult) {
        LOGGER.debug("Processing user create form={}, bindingResult={}", userCreateForm, bindingResult);
        if (bindingResult.hasErrors()) {
            // Failed validation
            return "users/user_create";
        }
        try {
            userService.create(userCreateForm);
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("Exception occurred when trying to save the user", e);
            bindingResult.reject("user.exists", "User already exists");
            return "users/user_create";
        }
        return "redirect:/users";
    }

    /*
        Create user's trips
    */
    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @GetMapping(value = "/{id}/trips/create")
    public ModelAndView getTripCreatePage(@PathVariable long id) {
        LOGGER.info("Getting trip create form");
        return new ModelAndView("trips/trip_create", "tripForm", new TripCreateForm());
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @PostMapping(value = "/{id}/trips/create")
    public String handleTripCreateForm(@Valid @ModelAttribute("tripForm") TripCreateForm form, BindingResult bindingResult, @PathVariable long id) {
        LOGGER.info("Processing trip create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // Failed validation
            return "trips/trip_create";
        }
        try {
            tripService.create(form, id);
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("Exception occurred when trying to save the trip", e);
            bindingResult.reject("trip.exists", "Trip already exists");
            return "trips/trip_create";
        }
        return "redirect:/user/{id}/trips";
    }

    /*
        Create user's flights
    */
    @PreAuthorize("@currentUserServiceImpl.canAccessFlights(principal, #id, #tripId)")
    @GetMapping(value = "/{id}/trips/{tripId}/flights/create")
    public ModelAndView getFlightCreatePage(@PathVariable long id, @PathVariable Integer tripId) {
        LOGGER.info("Getting trip create form");
        return new ModelAndView("flights/flight_create", "flightForm", new FlightCreateForm());
    }

    @PreAuthorize("@currentUserServiceImpl.canAccessFlights(principal, #id, #tripId)")
    @PostMapping(value = "/{id}/trips/{tripId}/flights/create")
    public String handleFlightCreateForm(@Valid @ModelAttribute("flightForm") FlightCreateForm form, BindingResult bindingResult, @PathVariable long id, @PathVariable Integer tripId) {
        LOGGER.info("Processing flight create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // Failed validation
            return "flights/flight_create";
        }
        try {
            flightService.create(form, tripId);
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("Exception occurred when trying to save the flight", e);
            bindingResult.reject("flight.exists", "Flight already exists");
            return "flights/flight_create";
        }
        return "redirect:/user/{id}/trips/{tripId}/flights";
    }

    /*
        Approve, deny, submit, CRUD operations on trip
    */
//    Approve
    @PreAuthorize("@currentUserServiceImpl.canChangeStatus(principal, #id, #tripId)")
    @GetMapping(path = "{id}/trips/{tripId}/approve")
    public String changeTripStatus(Model model,@PathVariable long id, @PathVariable Integer tripId) {
        model.addAttribute("trips", tripService.approveStatus(tripId));
        LOGGER.info("Approved trip={}", tripId);
        return "redirect:/user/{id}/trips";
    }

//    Deny
    @PreAuthorize("@currentUserServiceImpl.canChangeStatus(principal, #id, #tripId)")
    @GetMapping(path = "{id}/trips/{tripId}/deny")
    public String denyTripStatus(Model model,@PathVariable long id, @PathVariable Integer tripId) {
        model.addAttribute("trips", tripService.denyStatus(tripId));
        LOGGER.info("Denied trip={}", tripId);
        return "redirect:/user/{id}/trips";
    }

//    Submit
    @PreAuthorize("@currentUserServiceImpl.canSubmitStatus(principal, #id, #tripId)")
    @GetMapping(path = "{id}/trips/{tripId}/apply")
    public String applyTripStatus(Model model, @PathVariable long id, @PathVariable Integer tripId) {
        model.addAttribute("trips", tripService.applyStatus(tripId));
        LOGGER.info("Submitted for approval trip={}", tripId);
        return "redirect:/user/{id}/trips";
    }

//    Delete trip
    @PreAuthorize("@currentUserServiceImpl.canEditDeleteTrip(principal, #id, #tripId)")
    @GetMapping(path = "{id}/trips/{tripId}/delete")
    public String deleteTrip(Model model, @PathVariable long id, @PathVariable Integer tripId) {
        model.addAttribute("trips", tripService.deleteTrip(tripId));
        LOGGER.info("Deleted trip={}", tripId);
        return "redirect:/user/{id}/trips";
    }

//    Edit trip
    @PreAuthorize("@currentUserServiceImpl.canEditDeleteTrip(principal, #id, #tripId)")
    @GetMapping("/{id}/trips/{tripId}/edit")
    public ModelAndView getTripUpdatePage(@PathVariable long id,
                                            @PathVariable Integer tripId, Model model) {
        LOGGER.info("Getting trip update form");
        model.addAttribute("trip", tripService.getTripById(tripId));
        return new ModelAndView("trips/trip_update", "updateTrip", new TripCreateForm());
    }

    @PreAuthorize("@currentUserServiceImpl.canEditDeleteTrip(principal, #id, #tripId)")
    @PostMapping("/{id}/trips/{tripId}/edit")
    public String handleSaveUpdate(@Valid @ModelAttribute("updateTrip") TripCreateForm form, BindingResult bindingResult, @PathVariable long id, @PathVariable Integer tripId, Model model) {
        LOGGER.info("Processing trip update form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // Failed validation, reimplement model
            model.addAttribute("trip", tripService.getTripById(tripId));
            return "trips/trip_update";
        }
        try {
            tripService.updateTrip(tripId, form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("Exception occurred when trying to update the trip", e);
            bindingResult.reject("tripupdate.fail", "Trip update error");
            return "trips/trip_update";
        }
        return "redirect:/user/{id}/trips";
    }
}
