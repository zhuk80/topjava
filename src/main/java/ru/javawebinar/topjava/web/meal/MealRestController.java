package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Controller
public class MealRestController implements MealService {

    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Qualifier("mealServiceImpl")
    @Autowired
    private MealService service;

    @Override
    public Meal save(Meal meal) {
        return service.save(meal);
    }

    @Override
    public boolean delete(int id) throws NotFoundException {
        return service.delete(id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return service.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return service.getAll(userId);
    }


    @Override
    public Collection<MealWithExceed> getFilteredByDates(List<MealWithExceed> mealWithExceeds, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo) {
        return service.getFilteredByDates(mealWithExceeds, dateTimeFrom, dateTimeTo);
    }

    public Collection<MealWithExceed> getFilteredByTime(List<MealWithExceed> mealWithExceeds, LocalTime timeFromConverted, LocalTime timeToConverted) {
        return service.getFilteredByTime(mealWithExceeds, timeFromConverted, timeToConverted);
    }
}