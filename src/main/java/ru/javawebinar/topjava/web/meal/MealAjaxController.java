package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Evgeniy on 21.05.2017.
 */

@RestController
@RequestMapping("/ajax/meals")
public class MealAjaxController extends AbstractMealController {

    @Autowired
    private MealService mealService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void create (@RequestParam("id") Integer id,
                        @RequestParam("dateTime") String dateTime,
                        @RequestParam("description") String description,
                        @RequestParam("calories") int calories) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Meal meal = new Meal(id, LocalDateTime.parse(dateTime, formatter), description, calories);

        if (meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, id);
        }
    }

    @PostMapping(value = "/filter", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getBetween(@RequestParam(value = "startDate", required = false) LocalDate startDate,
                             @RequestParam(value = "startTime", required = false) LocalTime startTime,
                             @RequestParam(value = "endDate", required = false) LocalDate endDate,
                             @RequestParam(value = "endTime", required = false) LocalTime endTime, Model model) {

        //List<MealWithExceed> mealWithExceeds = super.getBetween(startDate, startTime, endDate, endTime);
        //String jsonMealWithExceeds = JsonUtil.writeValue(mealWithExceeds);
        System.out.println(startDate + " " + startTime + " " + " " +  endDate + " " + endTime);
        System.out.println();
        //return super.getBetween(startDate, startTime, endDate, endTime);
        //model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        //return "meals";
        return super.getBetween(startDate, startTime, endDate, endTime);
    }

    /*@GetMapping()
    public String meals(Model model) {
        model.addAttribute("meals",
                MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), 5));
        return "meals";
    }*/

}
