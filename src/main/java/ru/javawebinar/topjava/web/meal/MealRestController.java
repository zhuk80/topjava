package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @Autowired
    public MealRestController (MealService service) {
        super(service);
    }

    @Override
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    //@Override
    @PutMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@RequestBody Meal meal, Model model) {
        Meal resultMeal = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(resultMeal.getId()).toUri();

        //model.addAttribute("meal", resultMeal);
        //return resultMeal;
        return ResponseEntity.created(uriOfNewResource).body(resultMeal);
    }

    @Override
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    //@Override
    @GetMapping(value = "/getBetween/{startDateTime}/{endDateTime}", produces = MediaType.APPLICATION_JSON_VALUE)
    //public List<MealWithExceed> getBetween(@PathVariable("startDateTime") @DateTimeFormat (pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDateTime, @PathVariable ("endDateTime") @DateTimeFormat (pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDateTime) {
    public List<MealWithExceed> getBetween(@PathVariable ("startDateTime") LocalDateTime startDateTime, @PathVariable ("endDateTime")  LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}