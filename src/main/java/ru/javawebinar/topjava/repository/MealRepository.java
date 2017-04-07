package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {

    Meal save(Meal Meal, Integer userId);

    //Meal save(Meal Meal);

    boolean delete(int id);

    Meal get(int id);

    Collection<Meal> getAll(int userId);

    Collection<MealWithExceed> getFilteredByDates(List<MealWithExceed> mealWithExceeds, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    Collection<MealWithExceed> getFilteredByTime(List<MealWithExceed> mealWithExceeds, LocalTime timeFromConverted, LocalTime timeToConverted);
}
