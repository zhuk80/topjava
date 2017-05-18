package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {

    static final String REST_URL = "/rest/meals";

    @Autowired
    public MealRestController(MealService service) {
        super(service);
    }

    @Override
    @GetMapping(value = "/{id}")
    public Meal get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }


    //@Override
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> create(@RequestBody Meal meal, Model model) {
        Meal resultMeal = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(resultMeal.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(resultMeal);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Meal meal, @PathVariable("id") int id) {
        super.update(meal, id);
    }

    //@Override
    //@GetMapping(value = "/getBetween/{startDateTime}/{endDateTime}")
    @GetMapping(value = "/getBetween")
    //public List<MealWithExceed> getBetween(@PathVariable("startDateTime") @DateTimeFormat (pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDateTime, @PathVariable ("endDateTime") @DateTimeFormat (pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDateTime) {
    //public List<MealWithExceed> getBetween(@PathVariable("startDateTime") LocalDateTime startDateTime, @PathVariable("endDateTime") LocalDateTime endDateTime) {
    public List<MealWithExceed> getBetween(@RequestParam(value = "start") LocalDateTime startDateTime, @RequestParam(value = "end") LocalDateTime endDateTime) {
        LocalDate startDate = startDateTime.toLocalDate();
        LocalDate endDate = endDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();
        LocalTime endTime = endDateTime.toLocalTime();
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}